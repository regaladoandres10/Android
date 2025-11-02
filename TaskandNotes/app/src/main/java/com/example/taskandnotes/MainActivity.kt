package com.example.taskandnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.taskandnotes.core.Navigation.NavigationWrapper
import com.example.taskandnotes.ui.theme.TaskAndNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskAndNotesTheme {
                NavigationWrapper()
            }
        }
    }
}