package com.example.tasks.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tasks.static.FileType
import java.nio.file.Path

@Entity
data class Note(
    val title: String,
    val content: String,
    val dueDate: Long? = null,
    val reminderTime: Long?,
    val createdAt: Long = System.currentTimeMillis(),
    val lastModified: Long? = System.currentTimeMillis(),
    //Multimedia
    val filePath: String?,
    val fileType: FileType,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
