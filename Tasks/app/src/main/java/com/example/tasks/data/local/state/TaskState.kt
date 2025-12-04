package com.example.tasks.data.local.state

import com.example.tasks.data.local.SortTypeTask
import com.example.tasks.data.local.entities.Task
import com.example.tasks.data.local.events.TaskEvent
import com.example.tasks.static.FileType
import java.time.LocalDate
import java.time.LocalTime

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val title: String = "",
    val description: String = "",
    //Se utiliza Long porque la base de datos no soporta LocalTime
    val dueDate: Long? = null, //Campo de fecha
    //Campo de recordatorio
    val reminderTime: Long? = null,
    //Campos de multimedia
    val filePath: String? = null,
    val fileType: FileType = FileType.NONE,
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false,
    val isAddingTask: Boolean = false,
    val taskToEditId: Int? = null,
    val sortType: SortTypeTask = SortTypeTask.TODAS
)
