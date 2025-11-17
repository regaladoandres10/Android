package com.example.tasks.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StickyNote2
import androidx.compose.material.icons.outlined.Task
import com.example.tasks.static.BottomNavigationItem

val navItems = listOf(
    BottomNavigationItem(
        title = "Tareas",
        selectedIcon = Icons.Filled.Task,
        unSelectedIcon = Icons.Outlined.Task,
        hasNews = false,
    ),
    BottomNavigationItem(
        title = "Notas",
        selectedIcon = Icons.Filled.StickyNote2,
        unSelectedIcon = Icons.Outlined.StickyNote2,
        hasNews = false,
        badgeCount = 45
    ),
    BottomNavigationItem(
        title = "Configuraciones",
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
        hasNews = true,
    )
)