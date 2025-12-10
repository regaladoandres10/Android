package com.example.tasks.navigation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tasks.data.local.events.TaskEvent
import com.example.tasks.ui.common.AttachFileField
import com.example.tasks.ui.common.DatePickerFieldToModal
import com.example.tasks.ui.common.ReminderTimePickerField
import com.example.tasks.ui.common.TimesPicker
import com.example.tasks.viewmodel.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTask(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent

    //Determinar si estamos editando o creando
    val isEditing = state.taskToEditId != null
    val titleText =
        if (isEditing)
            "Editar Tarea"
        else "Crear Nueva Tarea"

    val scrollState = rememberScrollState()
    //Top bar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = titleText,
                    fontWeight = FontWeight.Bold
                ) },
                navigationIcon = {
                    IconButton( onClick = {
                        //Ocultar el modal de activo
                        onEvent(TaskEvent.hideModal)
                        navController.popBackStack()
                    } ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        //Contenido principal
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            //Name task
            Text(
                text = "Título de la Tarea",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                //fontWeight = FontWeight.Medium
            )
            //Campo de la tarea
            OutlinedTextField(
                value = state.title,
                onValueChange = { onEvent(TaskEvent.SetTitle(it)) },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            //Selector de fecha
            Text(
                text = "Fecha de vencimiento",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            DatePickerFieldToModal(
                selectedDateMillis = state.dueDate,
                onDateSelected = { onEvent(TaskEvent.SetDueDate(it)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            //Recordatorio
            Text(
                text = "Recordatorio",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            ReminderTimePickerField(
                reminderTime = state.reminderTime,
                onTimeSelected = { newTime ->
                    onEvent(TaskEvent.SetReminderTime(newTime))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            //Descripcion
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = state.description,
                onValueChange = { onEvent(TaskEvent.SetContent(it)) },
                label = { Text("Detalles de la tarea") },
                minLines = 3, // Permite múltiples líneas
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            //Adjuntar archivos
            Text(
                text = "Adjuntar Archivo",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )

            AttachFileField(
                currentFile = state.filePath,
                fileType = state.fileType,
                onFileAttached = { path, type ->
                    onEvent(TaskEvent.SetFile(path, type))
                }
            )
            Spacer(modifier = Modifier.height(24.dp))

            //Boton de crear tarea/guardar tarea
            Button(
                onClick = {
                    onEvent(TaskEvent.SaveTask)
                    //Una vez guardada volver a la lista de tareas
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                //Desabilitar si el titulo esta vacio
                enabled = state.title.isNotBlank()
            ) {
                Text(if (isEditing) "Guardar cambios" else "Crear tarea")
            }
        }
    }
}
