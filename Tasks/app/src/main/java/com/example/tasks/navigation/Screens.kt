package com.example.tasks.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StickyNote2
import androidx.compose.material.icons.outlined.Task
import com.example.tasks.models.BottomNavigationItem

val navItems = listOf(
    BottomNavigationItem(
        title = BottomBarScreen.Task.title,
        route = BottomBarScreen.Task.route,
        selectedIcon = BottomBarScreen.Task.selectedIcon,
        unSelectedIcon = BottomBarScreen.Task.unSelectedIcon,
        hasNews = BottomBarScreen.Task.hasNews,
    ),
    BottomNavigationItem(
        title = BottomBarScreen.Note.title,
        route = BottomBarScreen.Note.route,
        selectedIcon = BottomBarScreen.Note.selectedIcon,
        unSelectedIcon = BottomBarScreen.Note.unSelectedIcon,
        hasNews = BottomBarScreen.Note.hasNews,
        badgeCount = BottomBarScreen.Note.badgeCount
    ),
    BottomNavigationItem(
        title = BottomBarScreen.Configuration.title,
        route = BottomBarScreen.Configuration.route,
        selectedIcon = BottomBarScreen.Note.selectedIcon,
        unSelectedIcon = BottomBarScreen.Note.unSelectedIcon,
        hasNews = BottomBarScreen.Note.hasNews,
    )
)