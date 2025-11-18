package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.models.Task
import com.example.tasks.static.allTasks
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class MainViewModel: ViewModel() {

    //Estado para navBar
    private val _selectedItemIndex = MutableStateFlow(0)
    val selectedItemIndex = _selectedItemIndex.asStateFlow()

    //Estado para segmentedButtons
    private val _selectedIndexButton = MutableStateFlow(0)
    val selectedIndexButton = _selectedIndexButton.asStateFlow()

    //Texto a buscar
    private var _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    //Texto para la busqueda
    private var _isSearching = MutableStateFlow(false)
    var isSearching = _isSearching.asStateFlow()

    //Lista de tareas
    private val _tasks = MutableStateFlow(allTasks)
    val tasks = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        //Buscar la tarea
        .combine(_tasks) { text, tasks ->
            if(text.isBlank()) {
                tasks
            } else {
                tasks.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _tasks.value
        )
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onTaskSelected(selectedTask: Task) {
        //Recorremos la lista
        val updateList = _tasks.value.map { currentTask ->

            //Cambiar valor
            currentTask.copy(
                isCompleted = currentTask.title == selectedTask.title
            )
        }
        //Actualizamos la lista
        _tasks.value = updateList
    }

    fun selectedItem(index: Int) {
        _selectedItemIndex.value = index
    }

    fun selectedSegmentedButton(index: Int) {
        _selectedIndexButton.value  = index
    }
}

