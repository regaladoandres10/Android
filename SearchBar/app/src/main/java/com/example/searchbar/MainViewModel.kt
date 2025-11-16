package com.example.searchbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbar.models.Task
import com.example.searchbar.ui.theme.components.allTasks
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


@OptIn(FlowPreview::class)
class MainViewModel: ViewModel() {
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
        //Buscamos la tarea
        .combine(_tasks) { text, tasks ->
            if(text.isBlank()) {
                tasks
            } else {
                delay(2000L)
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

}