package com.example.tasks.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AttachmentOptionsDialog(
    onDismiss: () -> Unit,
    onTakePhoto: () -> Unit,
    onRecordVideo: () -> Unit,
    onRecordAudio: () -> Unit,
    onSelectGallery: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adjuntar archivo") },
        text = {
            Column {
                //Tomar foto
                TextButton(onClick = {
                    onDismiss()
                    onTakePhoto()
                }) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Tomar foto")
                    Spacer(Modifier.width(8.dp))
                    Text("Tomar foto")
                }
                //Grabar video
                TextButton(onClick = {
                    onDismiss()
                    onRecordVideo()
                }) {
                    Icon(Icons.Default.VideoLibrary, contentDescription = "Tomar video")
                    Spacer(Modifier.width(8.dp))
                    Text("Tomar video")
                }
                //Grabar audio
                TextButton(onClick = {
                    onDismiss()
                    onRecordAudio()
                }) {
                    Icon(Icons.Default.AudioFile, contentDescription = "Grabar audio")
                    Spacer(Modifier.width(8.dp))
                    Text("Grabar audio")
                }
                //Seleccionar en la galeria
                TextButton(onClick = {
                    onDismiss()
                    onSelectGallery
                }) {
                    Icon(Icons.Default.Attachment, contentDescription = "Seleccionar archivo")
                    Spacer(Modifier.width(8.dp))
                    Text("Seleccionar archivo")
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}