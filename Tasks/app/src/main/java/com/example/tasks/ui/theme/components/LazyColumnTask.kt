package com.example.tasks.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.MainViewModel

@Composable
fun LazyColumnTask(modifier: Modifier = Modifier) {
    val viewMoedel = viewModel<MainViewModel>()
    val tasks by viewMoedel.tasks.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(tasks) { task ->
            Text(
                text = task.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                )
        }
    }

}