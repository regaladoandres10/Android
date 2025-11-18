package com.example.tasks.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.MainViewModel
import com.example.tasks.navigation.Navigation
import com.example.tasks.navigation.navItems

@Composable
fun HomeScreen() {
    val viewModel = viewModel<MainViewModel>()
    val isSearching by viewModel.isSearching.collectAsState()
    //Contenido de navegación
    Scaffold(
        bottomBar = {
            Navigation(
                viewModel = viewModel,
                navItems = navItems
            )
        }
    ) { paddingValues ->
        //Contenido de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) //Evita que el contenido quede bajo el BottomBar
                .padding(16.dp)
        ) {
            Spacer( modifier = Modifier.height(35.dp) )
            Text(
                text = "Tareas",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Spacer( modifier = Modifier.height(16.dp) )
            SearchBar()
            Spacer( modifier = Modifier.height(16.dp) )
            //Mandar llamar el composable de LazyColumnTask
            if(isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                LazyColumnTask()
            }

        }
    }



}