package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.data.local.dao.NoteDao
import com.example.tasks.data.local.dao.TaskDao

class TaskViewModelFactory(private val daoTask: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            //Crea y retorna una nueva instancia de TaskViewModel con el DAO
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(daoTask) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
