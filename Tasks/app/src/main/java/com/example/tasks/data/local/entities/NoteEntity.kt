package com.example.tasks.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val reminderDate: Long? = null,
    val lastModified: Long? = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
