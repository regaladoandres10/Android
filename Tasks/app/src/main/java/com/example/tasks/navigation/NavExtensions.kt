package com.example.tasks.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToTaskDetails(taskId: Int) {
    this.navigate("${Destinations.TASK_DETAILS_ROUTE}/$taskId")
}

fun NavHostController.navigateToCreateTask(taskId: Int? = null) {
    val route = if (taskId != null) {
        "${Destinations.CREATE_TASK_ROUTE}?${Destinations.TASK_ID}=$taskId"
    } else {
        Destinations.CREATE_TASK_ROUTE
    }
    this.navigate(route)
}

fun NavHostController.navigateToCreateNote() {
    this.navigate(Destinations.CREATE_NOTE_ROUTE)
}