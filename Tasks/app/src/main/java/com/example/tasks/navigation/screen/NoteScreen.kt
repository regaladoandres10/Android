package com.example.tasks.navigation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.AppViewModelProvider
import com.example.tasks.data.local.entities.Note
import com.example.tasks.ui.common.TaskTopAppBar
import com.example.tasks.viewmodel.NoteEntryViewModel
import com.example.tasks.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navigateToNoteEntry: () -> Unit,
    navigateToNoteUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = viewModel(factory = AppViewModelProvider.factory)
) {

    val noteUiState by viewModel.noteUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Notas",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        NoteBody(
            noteList = noteUiState.noteList,
            onItemClick = navigateToNoteUpdate,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
private fun NoteBody(
    noteList: List<Note>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (noteList.isEmpty()) {
            Text(
                text = "No hay notas creadas",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            NoteList(
                noteList = noteList,
                onItemClick = { onItemClick(it) },
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
private fun NoteList(
    noteList: List<Note>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = noteList, key = { it.id }) { note ->
            NoteItem(
                note = note,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(note.id) }
            )
        }
    }
}

@Composable
private fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier.padding(bottom = 2.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}