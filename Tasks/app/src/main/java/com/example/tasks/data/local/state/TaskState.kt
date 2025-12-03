package com.example.tasks.data.local.state

import com.example.tasks.data.local.SortTypeTask
import com.example.tasks.data.local.entities.Task
import com.example.tasks.data.local.events.TaskEvent
import java.time.LocalDate
import java.time.LocalTime

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now().plusHours(1),
    val isCompleted: Boolean = false,
    val isAddingTask: Boolean = false,
    val sortType: SortTypeTask = SortTypeTask.TODAS
)
