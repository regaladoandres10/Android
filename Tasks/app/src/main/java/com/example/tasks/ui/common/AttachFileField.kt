package com.example.tasks.ui.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tasks.static.FileType
import com.example.tasks.static.determineFileType
import com.example.tasks.static.persistUriPermission

@Composable
fun AttachFileField(
    currentFile: String?,
    fileType: FileType,
    onFileAttached: (String?, FileType?) -> Unit
) {
    val context = LocalContext.current
    //Muestra el nombre del archivo si existe, sino "Ningún archivo adjunto"
    val fileName = currentFile?.substringAfterLast('/') ?: "Ningún archivo adjunto"

    //Seleccionar el archivo
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                //Determinar el tipo de archivo (PHOTO, VIDEO, etc.)
                val determinedType = determineFileType(context, uri)

                //Obtener la URI persistente como String para guardar en Room
                val persistentUri = persistUriPermission(context, uri)

                //Enviar el evento al ViewModel
                onFileAttached(persistentUri, determinedType)
            }
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(4.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = fileName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (currentFile != null) {
                Text(
                    text = "Tipo: ${fileType.name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Botón para adjuntar/reemplazar
        if (currentFile == null) {
            Button(onClick = {
                // TODO: Aquí invocar el ActivityResultLauncher para seleccionar archivo
                // La URI se almacena como String y se maneja el FileType en el launcher.
                filePickerLauncher.launch(arrayOf("*/*")) //Seleccionar cualquier archivo
            }) {
                Text("Adjuntar")
            }
        } else {
            // Botón para borrar el archivo adjunto
            IconButton(onClick = {
                onFileAttached(null, FileType.NONE) //Limpia el archivo
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remover archivo",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}