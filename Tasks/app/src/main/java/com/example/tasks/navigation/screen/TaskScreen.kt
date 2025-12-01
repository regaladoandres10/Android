package com.example.tasks.navigation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import com.example.tasks.ui.common.LazyColumnTask
import com.example.tasks.ui.common.SearchBar
import com.example.tasks.ui.common.SegmentedButtons

@Composable
fun TaskScreen(
    viewModel: MainViewModel = viewModel()
) {
    val isSearching by viewModel.isSearching.collectAsState()
    //Contenido de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
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
        SegmentedButtons()
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