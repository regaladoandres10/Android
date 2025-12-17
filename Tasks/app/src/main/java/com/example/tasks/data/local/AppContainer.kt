package com.example.tasks.data.local

import android.content.Context

interface AppContainer {
    val noteRepository: NotesRepository
    val taskRepository: TasksRepository
    val multimediaRepository: MultimediaRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val noteRepository: NotesRepository by lazy {
        OfflineNoteRepository(AppDatabase.getDatabase(context).noteDao())
    }
    override val taskRepository: TasksRepository by lazy {
        OfflineTaskRepository(AppDatabase.getDatabase(context).taskDao())
    }
    override val multimediaRepository: MultimediaRepository by lazy {
        OfflineMultimediaRepository(AppDatabase.getDatabase(context).mediaDao())
    }
}