package com.example.tasks.data.local

import com.example.tasks.data.local.dao.NoteDao
import com.example.tasks.data.local.entities.Note
import kotlinx.coroutines.flow.Flow

class OfflineNoteRepository(private val noteDao: NoteDao) : NotesRepository {
    override fun getAllNotesStream(): Flow<List<Note>> = noteDao.getAllItmes()
    override fun getNoteStream(id: Int): Flow<Note?> = noteDao.getNote(id)
    override suspend fun insertNote(item: Note) = noteDao.insertNote(item)
    override suspend fun updateNote(item: Note) = noteDao.updateNote(item)
    override suspend fun deleteNote(item: Note) = noteDao.deleteNote(item)
}
