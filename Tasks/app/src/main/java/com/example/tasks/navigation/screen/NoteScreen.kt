package com.example.tasks.navigation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tasks.MainViewModel
import com.example.tasks.navigation.AppScreen
import com.example.tasks.navigation.BottomBarScreen
import com.example.tasks.navigation.BottomNavGraph
import com.example.tasks.navigation.Navigation
import com.example.tasks.navigation.navItems
import com.example.tasks.ui.common.FloatingAddNoteButton
import com.example.tasks.ui.common.SearchBar

@Composable
fun NoteScreen(
    viewModel: MainViewModel = viewModel()
) {
    //Contenido de la pantala
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer( modifier = Modifier.height(20.dp) )
        Text(
            text = "Notas",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer( modifier = Modifier.height(5.dp) )
        //SearchBar()
    }


}