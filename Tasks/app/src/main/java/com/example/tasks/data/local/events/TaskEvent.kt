package com.example.tasks.data.local.events

import com.example.tasks.data.local.SortType
import com.example.tasks.models.Task

sealed interface TaskEvent {
    object SaveTask: TaskEvent
    data class SetTitle(val title: String): TaskEvent
    data class SetContent(val descripcion: String): TaskEvent
    data class SetDueDate(val dueDate: Long): TaskEvent
    data class SetIsCompleted(val isCompleted: Boolean): TaskEvent
    object showModal: TaskEvent
    object hideModal: TaskEvent
    data class SortTasks(val sortType: SortType): TaskEvent
    data class DeleteTask(val task: Task): TaskEvent
}