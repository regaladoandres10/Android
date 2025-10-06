package com.example.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier) {
        //Contador de agua
        StatefulCounter(modifier)
        val list = remember { getWellnessTasks().toMutableStateList() }
        //Lista de tareas
        WellnessTasksList(
            list = list,
            onCloseTask = { task -> list.remove(task) }
        )
    }
}

/**
 * Se generarn 32 tareas
 */
fun getWellnessTasks() = List(30) {
        i -> WellnessTask(i, "Task # $i")
}