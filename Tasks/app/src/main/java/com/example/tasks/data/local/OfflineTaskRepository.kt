package com.example.tasks.data.local

import com.example.tasks.data.local.dao.TaskDao
import com.example.tasks.data.local.entities.Task
import kotlinx.coroutines.flow.Flow

class OfflineTaskRepository(private val taskDao: TaskDao) : TasksRepository {
    override fun getAllITasksStream(): Flow<List<Task>> = taskDao.getAllTaskOrderByName()
    override fun getTaskStream(id: Int): Task? = taskDao.getTaskById(id)
    override suspend fun insertTask(item: Task) = taskDao.upsertTask(item)
    override suspend fun updateTask(item: Task) = taskDao.upsertTask(item)
    override suspend fun deleteTask(item: Task) = taskDao.deleteTask(item)
}