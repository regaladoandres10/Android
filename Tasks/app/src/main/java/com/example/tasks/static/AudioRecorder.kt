package com.example.tasks.static

import android.content.Context
import android.media.MediaRecorder
import java.io.File

class AudioRecorder(private val context: Context) {
    private var recorder: MediaRecorder? = null

    fun startRecording(file: File) {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(file.absolutePath)
            prepare()
            start()
        }
    }

    fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }
}