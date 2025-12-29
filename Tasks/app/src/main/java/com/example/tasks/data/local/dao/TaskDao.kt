package com.example.tasks.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.tasks.data.local.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    //Insertar si no existe y si existe lo remplaza
    @Upsert
    suspend fun upsertTask(task: Task): Long

    @Delete
    suspend fun deleteTask(task: Task)

    //Definir consultas
    //Mostrar todas las tareas ordenas por fecha
    @Query("SELECT * FROM task ORDER BY createdAt")
    fun getAllTaskOrderByName(): Flow<List<Task>>

    //Mostrar todas las tareas pendientes
    @Query("SELECT * FROM task WHERE isCompleted = 0 ORDER BY title")
    fun getEarringTask(): Flow<List<Task>>

    //Mostrar las tareas completadas
    @Query("SELECT * FROM task WHERE isCompleted = 1 ORDER BY title")
    fun getCompletedTask(): Flow<List<Task>>

    //Regresa una sola tarea
    @Query("SELECT * FROM task WHERE id = :taskId")
    fun getTaskById(taskId: Int): Task?
}