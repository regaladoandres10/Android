package com.example.tasks.navigation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.AppViewModelProvider
import com.example.tasks.ui.common.TaskTopAppBar
import com.example.tasks.viewmodel.NoteDetails
import com.example.tasks.viewmodel.NoteEntryViewModel
import com.example.tasks.viewmodel.NoteUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.factory)
    val uiState = viewModel.noteUiState
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TaskTopAppBar(
                title = "Nueva nota",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start
        ) {
            NoteEntryBody(
                itemUiState = uiState,
                onItemValueChange = viewModel::updateUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.saveNote()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .padding(innerPadding)
                    //.verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun NoteEntryBody(
    itemUiState: NoteUiState,
    onItemValueChange: (NoteDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)

    ) {
        NoteInputForm(
            itemDetails = itemUiState.noteDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = itemUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}

@Composable
fun NoteInputForm(
    itemDetails: NoteDetails,
    modifier: Modifier = Modifier,
    onValueChange: (NoteDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = itemDetails.title,
            onValueChange = { onValueChange(itemDetails.copy(title = it)) },
            label = {Text("Titulo")},
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = itemDetails.content,
            onValueChange = { onValueChange(itemDetails.copy(content = it)) },
            label = {Text("Descripción")},
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            minLines = 3,
            singleLine = false
        )
        if (enabled) {
            Text(
                text = "Requiere campos",
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}