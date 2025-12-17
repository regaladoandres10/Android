package com.example.tasks.ui.common

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

//@Composable
//fun TakePhotoButton(
//    onPhotoTaken: (Uri) -> Unit
//) {
//    val context = LocalContext.current
//    var photoUri by remember { mutableStateOf<Uri?>(null) }
//
//    val cameraLauncher =
//        rememberLauncherForActivityResult(
//            ActivityResultContracts.TakePicture()
//        ) { success ->
//            if (success) {
//                photoUri?.let { onPhotoTaken(it) }
//            }
//        }
//
//    Button(onClick = {
//        val photoFile = createImageFile(context)
//        photoUri = FileProvider.getUriForFile(
//            context,
//            "${context.packageName}.provider",
//            photoFile
//        )
//        cameraLauncher.launch(photoUri)
//    }) {
//        Text("Tomar foto")
//    }
//
//}

fun createImageFile(context: Context): File {
    val timestamp = System.currentTimeMillis()
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "IMG_${timestamp}_",
        ".jpg",
        storageDir
    )
}