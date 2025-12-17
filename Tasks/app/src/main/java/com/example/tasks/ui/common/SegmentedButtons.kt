package com.example.tasks.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.data.local.events.TaskEvent
import com.example.tasks.data.local.sortTypeMap
import com.example.tasks.static.options
import com.example.tasks.viewmodel.TaskViewModel

@Composable
fun SegmentedButtons(
    viewModel: TaskViewModel = viewModel()
) {
    //Obtener el estado
    val state by viewModel.state.collectAsState()
    //Acceder a la propiedad de sortType
    val currentSortType = state.sortType

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    val newSortType = sortTypeMap[index]
                    if (newSortType != null) {
                        viewModel.onEvent(TaskEvent.SortTasks(newSortType))
                    }
                },
                selected = currentSortType == sortTypeMap[index]
            ) {
                Text(text = option)
            }
        }
    }

}
