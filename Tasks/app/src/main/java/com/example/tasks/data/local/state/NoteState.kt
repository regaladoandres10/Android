package com.example.tasks.data.local.state

import com.example.tasks.data.local.SortTypeNote
import com.example.tasks.data.local.entities.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val title: String = "",
    val content: String = "",
    val reminderDate: Long? = null,
    val isAddingNote: Boolean = false,
    val sortType: SortTypeNote = SortTypeNote.TODAS
)
