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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.tasks.static.AudioRecorder
import com.example.tasks.static.FileType
import com.example.tasks.static.OwnerType
import com.example.tasks.ui.common.AttachFileField
import com.example.tasks.ui.common.AttachmentOptionsDialog
import com.example.tasks.ui.common.DatePickerFieldToModal
import com.example.tasks.ui.common.MediaList
import com.example.tasks.ui.common.ReminderTimePickerField
import com.example.tasks.ui.common.TimesPicker
import com.example.tasks.ui.common.createAudioFile
import com.example.tasks.ui.common.createImageFile
import com.example.tasks.ui.common.createVideoFile
import com.example.tasks.viewmodel.MediaDetails
import com.example.tasks.viewmodel.MediaDetailsViewModel
import com.example.tasks.viewmodel.MediaEntryViewModel
import com.example.tasks.viewmodel.TaskViewModel
import com.example.tasks.viewmodel.TaskViewModelFactory
import java.io.File


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

    val mediaDetailsViewModel:
            MediaDetailsViewModel = viewModel(factory = AppViewModelProvider.factory)

    val state by taskViewModel.state.collectAsState()
    val mediaState by mediaDetailsViewModel.mediaForOwner.collectAsState()
    val onEvent = taskViewModel::onEvent

    //Determinar si estamos editando o creando
    val isEditing = taskId != null
    //Dialog and camera state
    var showOptionsDialog by remember { mutableStateOf(false) }
    var photoUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var videoUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    //Estados para el audio
    val audioRecorder = remember { AudioRecorder(context) }
    var isRecordingAudio by remember { mutableStateOf(false) }
    var audioFile by remember { mutableStateOf<File?>(null) }

    fun toggleAudioRecording() {
        if (!isRecordingAudio) {
            val file = createAudioFile(context)
            audioFile = file
            audioRecorder.startRecording(file)
            isRecordingAudio = true
        } else {
            audioRecorder.stopRecording()
            isRecordingAudio = false

            audioFile?.let { file ->
                mediaDetailsViewModel.addLocalMedia(
                    MediaDetails(
                        uri = file.absolutePath,
                        type = FileType.AUDIO
                    )
                )
            }
        }
    }

    //Cargar datos si es edicion
    LaunchedEffect(taskId) {
        taskId?.let {
            taskViewModel.loadTaskForEdit(it)
            mediaDetailsViewModel.load(it, OwnerType.TASK)
        }
    }

    //Escuchar cuando la tarea se guarda
    LaunchedEffect(Unit) {
        taskViewModel.savedTaskId.collect { newTaskId ->
            mediaDetailsViewModel.saveAll(
                ownerId = newTaskId,
                ownerType = OwnerType.TASK
            )
            navigateBack()
        }
    }

    //Camera launcher
    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success && photoUri != null) {
                photoUri?.let { uri ->
                    mediaDetailsViewModel.addLocalMedia(
                        MediaDetails(
                            uri = uri.toString(),
                            type = FileType.PHOTO
                        )
                    )
                }
            }
        }

    val videoLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.CaptureVideo()
        ) { success ->
            if (success && videoUri != null) {
                mediaDetailsViewModel.addLocalMedia(
                    MediaDetails(
                        uri = videoUri.toString(),
                        type = FileType.VIDEO
                    )
                )
            } else {
                videoUri?.let { uri ->
                    File(uri.path ?: return@let).delete()
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

    val videoPermisionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                val videoFile = createVideoFile(context)
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    videoFile
                )
                videoUri = uri
                videoLauncher.launch(uri)
            }
        }

        //Permisos para el audio
        val audioPermissionLauncher =
            rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    val file = createAudioFile(context)
                    audioFile = file
                    audioRecorder.startRecording(file)
                    isRecordingAudio = true
                }
            }



//    //Guardar archivos despues de crear la tarea
//    LaunchedEffect(state.taskToEditId) {
//        if (!isEditing && state.taskToEditId != null) {
//            mediaDetailsViewModel.saveAll(
//                state.taskToEditId!!,
//                OwnerType.TASK
//            )
//            navigateBack()
//        }
//    }

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
            Spacer(modifier = Modifier.height(20.dp))

            //Media list
            if (mediaState.isNotEmpty()) {
                Text(
                    text = "Archivos adjuntos",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))

                MediaList(
                    media = mediaState,
                    onDelete = {
                        mediaDetailsViewModel.delete(it)
                    }
                )

            }

            Spacer(Modifier.height(16.dp))

            //Adjuntar archivos
//            Text(
//                text = "Adjuntar Archivo",
//                style = MaterialTheme.typography.titleMedium,
//                modifier = Modifier
//                    .padding(bottom = 4.dp)
//            )

            AttachFileField(
                currentFile = null,
                fileType = FileType.NONE,
                onFileAttached = { path, type ->
                    if (path != null && type != null) {
                        mediaDetailsViewModel.addLocalMedia(
                            MediaDetails(
                                uri = path,
                                type = type
                            )
                        )
                    }
                },
                //Open the showDialog
                onClick = { showOptionsDialog = true }
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (isRecordingAudio) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Grabando audio...",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            //Boton de crear tarea/guardar tarea
            Button(
                onClick = {
                    taskViewModel.onEvent(TaskEvent.SaveTask)
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
                onRecordVideo = {
                    showOptionsDialog = false

                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val videoFile = createVideoFile(context)

                        val uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.provider",
                            videoFile
                        )

                        videoUri = uri
                        videoLauncher.launch(uri)
                    } else {
                        videoPermisionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                onRecordAudio = {
                    showOptionsDialog = false

                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        toggleAudioRecording()
                    } else {
                        audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                },
                onSelectGallery = {}
            )
        }
    }
}
