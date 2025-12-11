package com.example.tasks

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tasks.viewmodel.NoteEntryViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            NoteEntryViewModel(taskApplication().container.noteRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [TaskApplication].
 */
fun CreationExtras.taskApplication(): TaskApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskApplication)