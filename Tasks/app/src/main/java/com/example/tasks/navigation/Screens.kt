package com.example.tasks.navigation

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
        selectedIcon = BottomBarScreen.Configuration.selectedIcon,
        unSelectedIcon = BottomBarScreen.Configuration.unSelectedIcon,
        hasNews = BottomBarScreen.Configuration.hasNews,
    )
)
//Rutas de pantallas
object AppScreen {
    const val CREATE_TASK = "create_task_route"
    const val CREATE_NOTE = "create_note_route"
}
