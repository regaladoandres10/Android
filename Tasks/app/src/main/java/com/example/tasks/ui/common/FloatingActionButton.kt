package com.example.tasks.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FloatingAddTaskButton(
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(
            Icons.Filled.Add,
            "Add task"
        ) },
        text = { Text("Nueva tarea") }
    )
}

@Composable
fun FloatingAddNoteButton(
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(
            Icons.Filled.Add,
            "Agregar notas"
        ) },
        text = { Text("Nueva nota") }
    )
}