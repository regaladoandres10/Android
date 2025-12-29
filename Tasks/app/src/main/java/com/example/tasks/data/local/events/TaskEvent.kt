package com.example.tasks.data.local.events

import com.example.tasks.data.local.SortTypeTask
import com.example.tasks.data.local.entities.Task
import com.example.tasks.static.FileType

sealed interface TaskEvent {
    object SaveTask: TaskEvent
    data class SetTitle(val title: String): TaskEvent
    data class SetContent(val descripcion: String): TaskEvent
    data class SetDueDate(val dueDate: Long?): TaskEvent
    data class SetIsCompleted(val task: Task, val isCompleted: Boolean): TaskEvent
    object showModal: TaskEvent
    object hideModal: TaskEvent
    data class SortTasks(val sortType: SortTypeTask): TaskEvent
    data class DeleteTask(val task: Task): TaskEvent
    data class SetTaskToEditId(val id: Int?): TaskEvent
    //Evento de recordatorio
    data class SetReminderTime(val time: Long?): TaskEvent
}