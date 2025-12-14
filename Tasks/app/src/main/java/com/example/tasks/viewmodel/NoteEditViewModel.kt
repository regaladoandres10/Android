package com.example.tasks.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.NotesRepository
import com.example.tasks.navigation.Destinations
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class NoteEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
): ViewModel() {
    /**
     * Holds current note ui state
     */
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    private val noteId: Int? = savedStateHandle[Destinations.NOTE_ID]

    init {
        noteId?.let { id ->
            viewModelScope.launch {
                notesRepository.getNoteStream(id)
                    .filterNotNull()
                    .firstOrNull()
                    ?.let { note ->
                        noteUiState = note.toNoteUiState()
                    }
            }
        }
    }

    /**
     * Update the note in the [NotesRepository] data source
     */
    suspend fun updateNote() {
        if (validateInput(noteUiState.noteDetails)) {
            notesRepository.updateNote(noteUiState.noteDetails.toItem())
        }
    }

    /**
     * Update the [noteUiState] with the value provide in the argument. This method also triggers
     * a validation for input values
     */
    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    //Validate fields
    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank()
        }
    }
}