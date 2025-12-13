package com.example.tasks.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tasks.data.local.NotesRepository
import com.example.tasks.data.local.entities.Note
import com.example.tasks.static.FileType

class NoteEntryViewModel(private val noteRepository : NotesRepository) : ViewModel() {

    var noteUiState by mutableStateOf(NoteUiState())

    //Actualiza la noteUiState con el valor
    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState = NoteUiState(
            noteDetails = noteDetails,
            isEntryValid = validateInput(noteDetails)
        )
    }

    //Inserta una nota en la base de datos
    suspend fun saveNote() {
        if(validateInput()) {
            noteRepository.insertNote(noteUiState.noteDetails.toItem())
        }
    }

    //Metodo de validación
    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank()
        }
    }

}

data class NoteUiState(
    val noteDetails: NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val createdAt: Long? = null,
    val lastModified: Long? = null,
    val filePath: String = "",
    val fileType: FileType? = FileType.NONE,
)

fun NoteDetails.toItem(): Note = Note(
    id = id,
    title = title,
    content = content,
    filePath = filePath.ifBlank { null },
    fileType = fileType ?: FileType.NONE
)

fun Note.toNoteUiState(isEntryValid: Boolean = false): NoteUiState = NoteUiState(
    noteDetails = this.toNoteDetails(),
    isEntryValid = isEntryValid
)

fun Note.toNoteDetails(): NoteDetails = NoteDetails(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    lastModified = lastModified,
    filePath = filePath ?: "",
    fileType = fileType
)