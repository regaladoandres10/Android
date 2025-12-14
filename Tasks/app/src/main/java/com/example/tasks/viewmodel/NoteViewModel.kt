package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.NotesRepository
import com.example.tasks.data.local.entities.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class NoteViewModel(
    notesRepository: NotesRepository
): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    data class NoteUiState(
        val noteList: List<Note> = listOf(),
        val query: String = ""
    )

    //State search
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //Add time for search
    val searchQuery = _searchText
        .debounce(300)
        .distinctUntilChanged()

    //Update search
    fun onSearchTextChange(text: String) {
        _searchText.value = text
        _isSearching.value = text.isNotBlank()
    }

    val noteUiState: StateFlow<NoteUiState> =
        combine(notesRepository.getAllNotesStream(), searchQuery) { notes, query ->
            val filteredNote = if (query.isBlank()) notes
            else notes.filter { note->
                note.title.contains(query, ignoreCase = true) }
            NoteUiState(noteList = filteredNote, query = query)
        }.onEach {
            _isSearching.value = false
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = NoteUiState()
        )
}

