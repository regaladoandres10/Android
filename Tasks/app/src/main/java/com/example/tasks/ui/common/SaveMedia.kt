package com.example.tasks.ui.common

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun saveMediaToInternalStorage(
    context: Context,
    uri: Uri,
    fileName: String
): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, fileName)
    val outputStream = FileOutputStream(file)

    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return file.absolutePath
}