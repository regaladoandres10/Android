package com.example.tasks.navigation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CreateTask() {
    val textTask by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        //Top bar

        //Name task or note
        Text(
            text = "Titulo",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray
        )
        OutlinedTextField(
            value = textTask,
            onValueChange = { textTask == it },
            label = {
                Text("Nombre de tarea")
            }
        )
        Spacer( modifier = Modifier.padding(16.dp) )
        //Date

    }
}
