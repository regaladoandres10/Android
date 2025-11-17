package com.example.tasks.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.MainViewModel
import com.example.tasks.models.Task
import com.example.tasks.static.allTasks

@Composable
fun RadioButtons() {
    val viewModel = viewModel<MainViewModel>()
    val tasks by viewModel.tasks.collectAsState()
    tasks.forEachIndexed { index, task ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                //Se selecciona el componente
                .clickable {
                    viewModel.onTaskSelected(task)
                }
                .padding(end = 16.dp)
        ) {
            RadioButton(
                selected = task.isCompleted,
                onClick = {
                    viewModel.onTaskSelected(task)
                }
            )
            Text(text = task.title)
        }
    }
}