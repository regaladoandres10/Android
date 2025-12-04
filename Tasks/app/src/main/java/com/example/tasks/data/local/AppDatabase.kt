package com.example.tasks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tasks.data.local.dao.TaskDao
import com.example.tasks.data.local.entities.Note
import com.example.tasks.data.local.entities.Task

@Database(
    entities =[Task::class, Note::class ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract val dao: TaskDao
    //abstract val dao: NoteDao
}