package com.example.tasks.ui.common

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.tasks.static.AudioRecorder
import java.io.File

@Composable
fun VoiceNoteButton() {
    val context = LocalContext.current
    val recorder = remember { AudioRecorder(context) }
    var recording by remember { mutableStateOf(false) }

    val file = remember {
        File(context.filesDir, "voice_${System.currentTimeMillis()}.m4a")
    }

    Button(onClick = {
        if (!recording) {
            recorder.startRecording(file)
        } else {
            recorder.stopRecording()
        }
        recording = !recording
    }) {
        Text(if (recording) "Stop recording" else "Record voice note")
    }
}
