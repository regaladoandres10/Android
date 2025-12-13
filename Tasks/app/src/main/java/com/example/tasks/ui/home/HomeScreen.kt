package com.example.tasks.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tasks.navigation.BottomBarScreen
import com.example.tasks.navigation.BottomNavGraph
import com.example.tasks.navigation.Destinations
import com.example.tasks.navigation.Navigation
import com.example.tasks.navigation.navItems
import com.example.tasks.navigation.navigateToCreateNote
import com.example.tasks.navigation.navigateToCreateTask
import com.example.tasks.ui.common.FloatingAddNoteButton
import com.example.tasks.ui.common.FloatingAddTaskButton

@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    //Observar la ruta actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigateToCreateTask: () -> Unit = {
        navController.navigateToCreateTask()
    }

    val navigateToCreateNote: () -> Unit = {
        navController.navigateToCreateNote()
    }

    val showFABTask = currentRoute == Destinations.TASK_ROUTE
    val showFABNote = currentRoute == Destinations.NOTES_ROUTE

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
            when {
                showFABTask -> FloatingAddTaskButton(navigateToCreateTask)
                showFABNote -> FloatingAddNoteButton(navigateToCreateNote)
            }
        }
    ) { paddingValues ->
        BottomNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}