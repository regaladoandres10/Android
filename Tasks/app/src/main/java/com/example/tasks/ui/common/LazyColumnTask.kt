package com.example.tasks.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.data.local.entities.Task
import com.example.tasks.data.local.events.TaskEvent
import com.example.tasks.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LazyColumnTask(
    tasks: List<Task>,
    onEditTask: (Int) -> Unit,
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent

    //Formatear el timestamp a una cadena de fecha/hora simple
    val formatTimestamp: (Long?) -> String = { timestamp ->
        if (timestamp == null) {
            "N/A"
        } else {
            //04/Dic/2025 10:30
            val formatter = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.getDefault())
            formatter.format(Date(timestamp))
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(tasks, key = { it.id }) { task ->
            TaskItem(
                task = task,
                onCompleteToggle = { isChecked ->
                    onEvent(TaskEvent.SetIsCompleted(task, isChecked))
                },
                onDelete = {
                    onEvent(TaskEvent.DeleteTask(task))
                },
                onEdit = {
                    //Cargar la tarea en el estado de edición del viewModel
                    viewModel.onEvent(TaskEvent.SetTaskToEditId(task.id))
                    //TODO: Navegación a pantalla de edicion
                    onEditTask(task.id)
                },
                formatTimestamp = formatTimestamp
            )
            //Separador entre cada tarea
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCompleteToggle: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    formatTimestamp: (Long?) -> String
) {
    //Mostrar u ocultar las acciones (editar/eliminar)
    var expanded by remember { mutableStateOf(false) }

    //Determinar el color de la tarjeta según el estado de la tarea
    val cardColor = if (task.isCompleted) {
        MaterialTheme.colorScheme.surfaceVariant
    //Color para tareas completadas
    } else {
        MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                //Abrir las opciones o navegar a detalles
                expanded = !expanded // Ejemplo: Mostrar opciones al tocar
            },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Checkbox para Completar/Incompletar
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = onCompleteToggle, //Envía el evento al ViewModel
                    modifier = Modifier.padding(end = 8.dp)
                )

                //Título y Descripción
                Column(modifier = Modifier.weight(1f)) {
                    //Título (Tachado si está completada)
                    Text(
                        text = task.title ?: "Sin título",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        style = if (task.isCompleted)
                            TextStyle(textDecoration = TextDecoration.LineThrough)
                        else LocalTextStyle.current,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    //Descripción
                    if (task.description?.isNotBlank() == true) {
                        Text(
                            text = task.description,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                //Icono de Menú desplegable de opciones
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Acciones"
                    )
                }

                //Menú de Acciones (Eliminar/Editar)
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = {
                            onEdit()
                            expanded = false
                        },
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "Editar") }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            onDelete() //Envía el evento de eliminación
                            expanded = false
                        },
                        leadingIcon = { Icon(Icons.Default.Delete, contentDescription = "Eliminar") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                //Fecha de Creación
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Fecha de creación",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Creada: ${formatTimestamp(task.createdAt)}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                //Recordatorio (Solo si existe)
                if (task.reminderTime != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Recordatorio",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.error //Color destacado para el recordatorio
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Recordatorio: ${formatTimestamp(task.reminderTime)}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                //Archivo Adjunto (Solo si existe)
                if (task.filePath != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AttachFile,
                            contentDescription = "Archivo Adjunto",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Adjunto: ${task.fileType?.name ?: "Archivo"}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}