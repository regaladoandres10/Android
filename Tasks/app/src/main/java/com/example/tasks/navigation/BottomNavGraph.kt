package com.example.tasks.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasks.navigation.screen.NoteScreen
import com.example.tasks.navigation.screen.SettingScreen
import com.example.tasks.navigation.screen.TaskScreen
import com.example.tasks.ui.home.HomeScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Task.route
    ) {
        //Agregando las pantallas
        composable(route = BottomBarScreen.Task.route) {
            TaskScreen()
        }
        composable(route = BottomBarScreen.Note.route) {
            NoteScreen()
        }
        composable( route = BottomBarScreen.Configuration.route ) {
            SettingScreen()
        }
    }
}

