package com.example.tasks.data.local

import com.example.tasks.data.local.entities.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllITasksStream(): Flow<List<Task>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getTaskStream(id: Int): Flow<Task?>

    /**
     * Insert item in the data source
     */
    suspend fun insertTask(item: Task)

    /**
     * Delete item from the data source
     */
    suspend fun deleteTask(item: Task)

    /**
     * Update item in the data source
     */
    suspend fun updateTask(item: Task)
}