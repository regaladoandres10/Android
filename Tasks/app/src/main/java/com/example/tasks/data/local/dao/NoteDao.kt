package com.example.tasks.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tasks.data.local.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    //Insertar notas
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    //Definir consultas
    //Mostrar todas las notas
    @Query("SELECT * FROM note ORDER BY createdAt ASC")
    fun getAllItmes(): Flow<List<Note>>

    //Obtener un elemento de la tabla de Note
    @Query("SELECT * FROM note WHERE id = :id")
    fun getNote(id: Int): Flow<Note?>
}