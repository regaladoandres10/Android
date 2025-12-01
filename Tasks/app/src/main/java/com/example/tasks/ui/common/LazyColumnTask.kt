package com.example.tasks.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.MainViewModel

@Composable
fun LazyColumnTask(modifier: Modifier = Modifier) {
    val viewModel = viewModel<MainViewModel>()
    val tasks by viewModel.tasks.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(tasks) { task ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                RadioButton(
                    selected = task.isCompleted,
                    onClick = {
                        viewModel.onTaskSelected(task)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = task.title)
                //TODO: Investigar como poner currentTimeMillis en texto
                //Text(text = task.createdAt.toDouble().toString())
            }
        }
    }
}

@Composable
fun ItemTask() {

    //Icono de task o note

    //Nombre de la tarea o nota

    //Fecha limite

}