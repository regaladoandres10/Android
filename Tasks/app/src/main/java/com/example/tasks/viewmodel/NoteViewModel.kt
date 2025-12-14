package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.NotesRepository
import com.example.tasks.data.local.entities.Note
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NoteViewModel(
    notesRepository: NotesRepository
): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    data class NoteUiState(val noteList: List<Note> = listOf())

    val noteUiState: StateFlow<NoteUiState> =
        notesRepository.getAllNotesStream().map { NoteUiState(it) }
            //Convertir un state en StateFlow
            .stateIn(
                scope = viewModelScope, //Define el ciclo de vida de StateFlow
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                //Establecer el valor de inicio del flujo del estado
                initialValue = NoteUiState()
            )
}

