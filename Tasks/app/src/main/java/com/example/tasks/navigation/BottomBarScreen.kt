package com.example.tasks.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Task
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tasks.navigation.Destinations

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
) {
    data object Task : BottomBarScreen(
        route = Destinations.TASK_ROUTE,
        title = "Tareas",
        selectedIcon = Icons.Filled.Task,
        unSelectedIcon = Icons.Outlined.Task,
        hasNews = false
    )
    data object Note : BottomBarScreen(
        route = Destinations.NOTES_ROUTE,
        title = "Notas",
        selectedIcon = Icons.Filled.Notes,
        unSelectedIcon = Icons.Outlined.Notes,
        hasNews = false,
        badgeCount = null
    )
    data object Configuration : BottomBarScreen(
        route = Destinations.SETTINGS_ROUTE,
        title = "Configuracion",
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
        hasNews = true
    )
}