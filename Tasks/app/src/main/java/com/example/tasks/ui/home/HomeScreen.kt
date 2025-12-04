package com.example.tasks.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tasks.navigation.AppScreen
import com.example.tasks.navigation.BottomBarScreen
import com.example.tasks.navigation.BottomNavGraph
import com.example.tasks.navigation.Navigation
import com.example.tasks.navigation.navItems
import com.example.tasks.ui.common.FloatingAddNoteButton
import com.example.tasks.ui.common.FloatingAddTaskButton
import com.example.tasks.viewmodel.TaskViewModelFactory

@Composable
fun HomeScreen(taskViewModelFactory: TaskViewModelFactory) {
    val navController = rememberNavController()
    //Observar la ruta actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigateToCreateTask = {
        navController.navigate(AppScreen.CREATE_TASK)
    }

    val navigateToCreateNote = {
        navController.navigate(AppScreen.CREATE_NOTE)
    }

    val showFABTask = when (currentRoute) {
        BottomBarScreen.Task.route -> true
        //Ocultar el FAB
        AppScreen.CREATE_TASK -> false
        //Ocultar en cualquier otra ruta no especificada
        else -> false
    }

    val showFABNote = when (currentRoute) {
        BottomBarScreen.Note.route -> true
        //Ocultar FAB
        AppScreen.CREATE_NOTE -> false
        else -> false
    }

    //Contenido de navegación
    Scaffold(
        //Navegacion
        bottomBar = {
            Navigation(
                navController = navController,
                navItems = navItems,
            )
        },
        //Botón flotante para agregar tareas
        floatingActionButton = {
            if (showFABTask) {
                FloatingAddTaskButton(navigateToCreateTask)
            }
            if (showFABNote) {
                FloatingAddNoteButton { navigateToCreateNote }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            BottomNavGraph(
                navController = navController,
                taskViewModelFactory = taskViewModelFactory
            )
        }
    }
}