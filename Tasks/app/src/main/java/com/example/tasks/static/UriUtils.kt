package com.example.tasks.static

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

//Saber el tipo de archivo
fun determineFileType(context: Context, uri: Uri): FileType {
    val mimeType = context.contentResolver.getType(uri) ?: "unknown"

    return when {
        mimeType.startsWith("image/") -> FileType.PHOTO
        mimeType.startsWith("video/") -> FileType.VIDEO
        mimeType.startsWith("audio/") -> FileType.AUDIO
        //Agregar más tipos de documentos aquí (e.g., application/pdf)
        else -> FileType.DOCUMENT
    }
}

//Función para asegurar la persistencia del permiso para la URI
fun persistUriPermission(context: Context, uri: Uri): String {
    val contentResolver = context.contentResolver
    val flags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION

    // Otorga permisos persistentes de lectura para el URI
    try {
        contentResolver.takePersistableUriPermission(uri, flags)
    } catch (e: Exception) {
        //Manejar la excepción si la URI no soporta permisos persistentes
        e.printStackTrace()
    }
    //Devolvemos el string del URI para almacenarlo en Room.
    return uri.toString()
}