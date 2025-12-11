package com.example.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.tasks.data.local.AppDatabase
import com.example.tasks.ui.theme.TasksTheme
import com.example.tasks.ui.home.HomeScreen
import com.example.tasks.viewmodel.TaskViewModelFactory
import kotlin.getValue


class MainActivity : ComponentActivity() {

    //Inicializa la base de datos y obtiene el DAO
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "task_db"
        ).build()
    }

    //Obtener la instancia del DAO
    private val daoTask by lazy {
        database.taskDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasksTheme {

                //Crear la instancia de la fabrica aquí, usando el DAO inicializado
                val taskViewModelFactory = TaskViewModelFactory(daoTask)
                HomeScreen(
                    taskViewModelFactory = taskViewModelFactory
                )
            }
        }
    }
}
