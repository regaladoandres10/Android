package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tasks.data.local.dao.TaskDao

class TaskViewModel(
    private val dao: TaskDao
): ViewModel() {

}