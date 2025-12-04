package com.example.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.tasks.data.local.AppDatabase
import com.example.tasks.ui.theme.TasksTheme
import com.example.tasks.ui.home.HomeScreen
import kotlin.getValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasksTheme {
                HomeScreen()
            }
        }
    }
}
