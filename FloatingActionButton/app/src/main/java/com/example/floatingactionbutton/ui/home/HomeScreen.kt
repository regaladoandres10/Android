package com.example.floatingactionbutton.ui.home

import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.floatingactionbutton.ui.components.FloatingAddTaskButton

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val onFabClick = {
        Toast.makeText(context, "Boton presionado", Toast.LENGTH_SHORT).show()
    }
    Scaffold(
        floatingActionButton = {
            FloatingAddTaskButton(onClick = onFabClick)
        }
    ) { }
}