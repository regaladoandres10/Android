package com.example.tasks.static

import android.content.Context
import android.net.Uri

//Saber el tipo de archivo
fun determineFileType(context: Context, uri: Uri) : FileType {
    val mimeType = context.contentResolver.getType(uri) ?: "Unknown"

    return when {
        mimeType.startsWith("image/") -> FileType.PHOTO
        mimeType.startsWith("video/") -> FileType.VIDEO
        mimeType.startsWith("audio/") -> FileType.AUDIO
        else -> FileType.DOCUMENT
    }
}

//Función para asegurar la persistencia del permiso para la URI
fun persistUriPermission(context: Context, uri: Uri) : String {
    val contentResolver = context.contentResolver
    val flags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION

    try {
        contentResolver.takePersistableUriPermission(uri, flags)
    } catch ( e: Exception ) {
        e.printStackTrace()
    }
    return uri.toString()
}