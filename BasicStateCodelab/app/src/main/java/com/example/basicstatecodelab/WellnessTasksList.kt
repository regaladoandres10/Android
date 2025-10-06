package com.example.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier



@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    onCloseTask: (WellnessTask) -> Unit,
    //Creando un estado de tipo lista que contenga los 32 items de la lista
    list: List<WellnessTask> = remember { getWellnessTasks() }
) {
    LazyColumn (modifier = Modifier) {
        //Obtenemos la lista
        items(
            items = list,
            key = {task -> task.id}
        ) {
            task -> WellnessTaskItem(taskName = task.label, onClose = { onCloseTask(task) })
        }
    }
}
