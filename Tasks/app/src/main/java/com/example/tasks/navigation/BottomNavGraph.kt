package com.example.tasks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tasks.data.local.events.TaskEvent
import com.example.tasks.navigation.screen.CreateTask
import com.example.tasks.navigation.Destinations
import com.example.tasks.navigation.screen.NoteEntryBody
import com.example.tasks.navigation.screen.NoteEntryScreen
import com.example.tasks.navigation.screen.NoteScreen
import com.example.tasks.navigation.screen.SettingScreen
import com.example.tasks.navigation.screen.TaskScreen
import com.example.tasks.viewmodel.NoteEntryViewModel
import com.example.tasks.viewmodel.TaskViewModel
import com.example.tasks.viewmodel.TaskViewModelFactory

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.TASK_ROUTE,
        modifier = modifier
    ) {
        //Screens
        composable(route = Destinations.TASK_ROUTE) {
            TaskScreen(
                navigateToCreateTask = { taskId ->
                    navController.navigateToCreateTask(taskId)
                },
                navigateToTaskDetails = { taskId ->
                    navController.navigateToTaskDetails(taskId)
                }
            )
        }
        composable(route = Destinations.NOTES_ROUTE) {
            NoteScreen(
                navigateToNoteEntry = {
                    navController.navigateToCreateNote()
                }
            )
        }
        //Create notes
        composable(route = Destinations.CREATE_NOTE_ROUTE) {
            NoteEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable( route = Destinations.SETTINGS_ROUTE ) {
            SettingScreen()
        }

        //Pantalla de creacion y edición de tareas
        composable(
            route = Destinations.CREATE_TASK_WITH_ARGS,
            arguments = listOf(
                navArgument(Destinations.TASK_ID) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(Destinations.TASK_ID) ?: -1

            CreateTask(
                taskId = if (taskId == -1) null else taskId,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

