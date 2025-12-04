package com.example.tasks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tasks.data.local.events.TaskEvent
import com.example.tasks.navigation.screen.CreateTask
import com.example.tasks.navigation.screen.NoteScreen
import com.example.tasks.navigation.screen.SettingScreen
import com.example.tasks.navigation.screen.TaskScreen
import com.example.tasks.viewmodel.TaskViewModel
import com.example.tasks.viewmodel.TaskViewModelFactory

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    taskViewModelFactory: TaskViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Task.route
    ) {
        //Agregando las pantallas
        composable(route = BottomBarScreen.Task.route) {
            //Inyectar TaskViewModel con la factory
            val taskViewModel: TaskViewModel = viewModel(factory = taskViewModelFactory)
            TaskScreen(
                navController = navController,
                taskViewModel = taskViewModel
            )
        }
        composable(route = BottomBarScreen.Note.route) {
            NoteScreen()
        }
        composable( route = BottomBarScreen.Configuration.route ) {
            SettingScreen()
        }
        //Pantalla de creacion y edición de tareas
        composable(
            route = "${AppScreen.CREATE_TASK}?taskId={taskId}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val taskViewModel: TaskViewModel = viewModel(factory = taskViewModelFactory)

            //Lógica de carga de tarea (solo si se está editando)
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            LaunchedEffect(taskId) {
                if (taskId != -1) {
                    taskViewModel.loadTaskForEdit(taskId)
                } else {
                    //Limpiar el estado de edición al crear una nueva
                    taskViewModel.onEvent(TaskEvent.SetTaskToEditId(null))
                }
            }
            CreateTask(navController = navController)
        }
    }
}

