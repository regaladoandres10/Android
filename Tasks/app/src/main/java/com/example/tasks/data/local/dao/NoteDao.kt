package com.example.tasks.data.local.dao

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.tasks.data.local.entities.Note
import kotlinx.coroutines.flow.Flow

interface NoteDao {
    //Insertar si no existe y si existe lo remplaza
    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    //Definir consultas
    //Mostrar todas las notas
    @Query("SELECT * FROM note ORDER BY title")
    fun getAllNotesOrderByTitle(): Flow<List<Note>>
}