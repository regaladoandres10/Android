package com.example.tasks.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.NotesRepository
import com.example.tasks.navigation.Destinations
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NoteDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
): ViewModel() {
    //Asignar la id desde Destinations.note_id
    private val noteId: Int = checkNotNull(savedStateHandle[Destinations.NOTE_ID])

    /**
     * Holds the item details ui state. The data is retrieved from [ItemsRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<NoteDetailsUiState> =
        notesRepository.getNoteStream(noteId)
            .filterNotNull()
            .map {
                NoteDetailsUiState(noteDetails = it.toNoteDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = NoteDetailsUiState()
            )

    /**
     * Deletes the note from the [NotesRepository] data source
     */
    suspend fun deleteNote() {
        notesRepository.deleteNote(uiState.value.noteDetails.toItem())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI State for NoteDetailsScreen
 */
data class NoteDetailsUiState(
    val noteDetails: NoteDetails = NoteDetails()
)