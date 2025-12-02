package com.example.tasks.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.tasks.navigation.AppScreen
import com.example.tasks.navigation.BottomNavGraph
import com.example.tasks.navigation.Navigation
import com.example.tasks.navigation.navItems
import com.example.tasks.ui.common.FloatingAddTaskButton

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val navigateToCreateTask = {
        navController.navigate(AppScreen.CREATE_TASK)
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
            FloatingAddTaskButton(navigateToCreateTask)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            BottomNavGraph(navController)
        }
    }



}