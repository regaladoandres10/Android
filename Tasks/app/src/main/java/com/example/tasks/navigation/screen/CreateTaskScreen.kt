package com.example.tasks.navigation.screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tasks.AppViewModelProvider
import com.example.tasks.data.local.AppDatabase
import com.example.tasks.data.local.events.TaskEvent
import com.example.tasks.static.FileType
import com.example.tasks.static.OwnerType
import com.example.tasks.ui.common.AttachFileField
import com.example.tasks.ui.common.AttachmentOptionsDialog
import com.example.tasks.ui.common.DatePickerFieldToModal
import com.example.tasks.ui.common.ReminderTimePickerField
import com.example.tasks.ui.common.TimesPicker
import com.example.tasks.ui.common.createImageFile
import com.example.tasks.viewmodel.MediaDetails
import com.example.tasks.viewmodel.MediaEntryViewModel
import com.example.tasks.viewmodel.TaskViewModel
import com.example.tasks.viewmodel.TaskViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTask(
    taskId: Int?,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
) {

    val context = LocalContext.current
    val dao = AppDatabase.getDatabase(context).taskDao()
    //State for pick a file
    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(dao)
    )

    val mediaViewModel: MediaEntryViewModel = viewModel(factory = AppViewModelProvider.factory)

    val state by taskViewModel.state.collectAsState()
    val onEvent = taskViewModel::onEvent

    //Dialog and camera state
    var showOptionsDialog by remember { mutableStateOf(false) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    //Camera launcher
    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                photoUri?.let { uri ->
                    mediaViewModel.updateUiState(
                        MediaDetails(
                            ownerId = taskId ?: 0,
                            ownerType = OwnerType.TASK,
                            uri = uri.toString(),
                            type = FileType.PHOTO
                        )
                    )
                    mediaViewModel.saveMedia()
                }
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                val photoFile = createImageFile(context)
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    photoFile
                )
                photoUri = uri
                cameraLauncher.launch(uri)
            }
        }

    //Uploading task
    LaunchedEffect(taskId) {
        taskId?.let {
            taskViewModel.loadTaskForEdit(taskId)
        }
    }

    //Determinar si estamos editando o creando
    val isEditing = taskId != null
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
                        onNavigateUp()
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
                    //onEvent(TaskEvent.SetFile(path, type))
                },
                //Open the showDialog
                onClick = {
                    showOptionsDialog = true
                }
            )
            Spacer(modifier = Modifier.height(24.dp))

            //Boton de crear tarea/guardar tarea
            Button(
                onClick = {
                    onEvent(TaskEvent.SaveTask)
                    //Una vez guardada volver a la lista de tareas
                    navigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                //Desabilitar si el titulo esta vacio
                enabled = state.title.isNotBlank()
            ) {
                Text(if (isEditing) "Guardar cambios" else "Crear tarea")
            }
        }
        if (showOptionsDialog) {
            AttachmentOptionsDialog(
                onDismiss = { showOptionsDialog = false },
                onTakePhoto = {
                    showOptionsDialog = false
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        //Permisos ya concendidos
                        //Create file
                        val photoFile = createImageFile(context)
                        //Convertir el File a Uri
                        val uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.provider",
                            photoFile
                        )
                        //Guardar uri en el estado
                        photoUri = uri
                        //Lanzar camara
                        cameraLauncher.launch(uri)
                    } else {
                        //Pedir permisos
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                onRecordVideo = {},
                onRecordAudio = {},
                onSelectGallery = {}
            )
        }
    }
}
