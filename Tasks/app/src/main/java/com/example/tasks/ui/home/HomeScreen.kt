package com.example.tasks.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.tasks.MainViewModel
import com.example.tasks.navigation.BottomNavGraph
import com.example.tasks.navigation.Navigation
import com.example.tasks.navigation.navItems
import com.example.tasks.ui.components.FloatingAddTaskButton
import com.example.tasks.ui.components.LazyColumnTask
import com.example.tasks.ui.components.SearchBar
import com.example.tasks.ui.components.SegmentedButtons

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    //Funcion temporal
    val onFabClick = {
        Toast.makeText(context, "Boton presionado", Toast.LENGTH_SHORT).show()
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
            FloatingAddTaskButton(onFabClick)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            BottomNavGraph(navController)
        }
    }



}