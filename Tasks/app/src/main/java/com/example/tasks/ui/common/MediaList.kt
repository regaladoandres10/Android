package com.example.tasks.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tasks.data.local.entities.Multimedia
import com.example.tasks.static.FileType
import com.example.tasks.viewmodel.MediaDetails

@Composable
fun MediaList(
    media: List<MediaDetails>,
    onDelete: (MediaDetails) -> Unit
) {
    Column {
        media.forEach { item ->
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Icono según tipo
                Icon(
                    imageVector = when (item.type) {
                        FileType.PHOTO -> Icons.Default.Image
                        FileType.VIDEO -> Icons.Default.Videocam
                        FileType.AUDIO -> Icons.Default.AudioFile
                        else -> Icons.Default.AttachFile
                    },
                    contentDescription = null
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = item.uri.substringAfterLast('/'),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(onClick = { onDelete(item) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

