package com.example.tasks

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tasks.viewmodel.MediaDetailsViewModel
import com.example.tasks.viewmodel.MediaEntryViewModel
import com.example.tasks.viewmodel.NoteDetailsViewModel
import com.example.tasks.viewmodel.NoteEditViewModel
import com.example.tasks.viewmodel.NoteEntryViewModel
import com.example.tasks.viewmodel.NoteViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {

        initializer {
            NoteEntryViewModel(taskApplication().container.noteRepository)
        }

        initializer {
            NoteViewModel(taskApplication().container.noteRepository)
        }

        initializer {
            NoteDetailsViewModel(
                this.createSavedStateHandle(),
                taskApplication().container.noteRepository
            )
        }

        initializer {
            NoteEditViewModel(
                this.createSavedStateHandle(),
                taskApplication().container.noteRepository
            )
        }

        initializer {
            MediaEntryViewModel(taskApplication().container.multimediaRepository)
        }

        initializer {
            MediaDetailsViewModel(
                this.createSavedStateHandle(),
                taskApplication().container.multimediaRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [TaskApplication].
 */
fun CreationExtras.taskApplication(): TaskApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskApplication)