package com.example.tasks.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    val title: String?,
    val description: String?,
    val dueDate: Long?,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    //Id
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)