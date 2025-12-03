package com.example.roomjetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomjetpackcompose.data.local.SortType
import com.example.roomjetpackcompose.data.local.dao.ContactDao
import com.example.roomjetpackcompose.data.local.entities.Contact
import com.example.roomjetpackcompose.data.local.events.ContactEvent
import com.example.roomjetpackcompose.data.local.state.ContactState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ContactViewModel(
    private val dao: ContactDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    //Guardar el tipo de ordenamiento actual
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.FIRST_NAME -> dao.getContactOrderedByFirstName()
                SortType.LAST_NAME -> dao.getContactOrderedByLastName()
                SortType.PHONE_NUMBER -> dao.getContactOrderedByPhoneNumber()
            }
        }
        //Crear un flujo de estado
        //viewModelScope = Asegura que el flow esta activo mientras el viewModel vive.
        //SharingStarted.WhileSubscribed() = Optimiza el consumo
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    //Estado general del estado
    private val _state = MutableStateFlow(ContactState())
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        //Combinar tres flujos en un solo flujo
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun onEvent(event: ContactEvent) {
        when(event) {
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }
            //Insertar contacto
            ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber
                //Verificar si algun campo esta vacio
                if( firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank() ) {
                    //No actualizamos nada
                    return
                }

                //Insertar en la base de datos
                val contact = Contact(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber
                )
                viewModelScope.launch {
                    //Pasamos el dato que queramos insertar en la base de datos
                    dao.upsertContact(contact)
                }
                //Actualizamos o reiniciamos el formulario
                _state.update { it.copy(
                    isAddingContact = false,
                    firstName = "",
                    lastName = "",
                    phoneNumber = ""
                ) }
            }
            is ContactEvent.SetFirstName -> {
                _state.update { it.copy(
                    firstName = event.firstName
                ) }
            }
            is ContactEvent.SetLastName -> {
                _state.update { it.copy(
                    lastName = event.lastName
                ) }
            }
            is ContactEvent.SetPhoneNumber -> {
                _state.update { it.copy(
                    phoneNumber = event.phoneNumber
                ) }
            }
            //Orenar los contactos
            is ContactEvent.SortContacts -> {
                _sortType.value = event.sortType
            }
            //Ocultar el formulario
            ContactEvent.hideDialog -> {
                _state.update { it.copy(
                    isAddingContact = false
                ) }
            }
            //Muestra el formulario
            ContactEvent.showDialog -> {
                _state.update { it.copy(
                    isAddingContact = true
                ) }
            }
        }
    }

}