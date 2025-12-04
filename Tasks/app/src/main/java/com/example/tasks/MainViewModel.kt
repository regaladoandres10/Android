package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun selectedItem(index: Int) {
        _selectedItemIndex.value = index
    }

    fun selectedSegmentedButton(index: Int) {
        _selectedIndexButton.value  = index
    }
}

