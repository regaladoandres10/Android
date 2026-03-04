package com.example.appsice.workers

import android.content.Context
import android.content.ContextParams
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.appsice.SNApplication
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CalificacionFinalWorker(
    ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx, params) {
    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        val container = (applicationContext as SNApplication).container
        val repository = container.snRepository

        val calificacionFinal = repository.getCaliFinal(2)

        //Convertimos el objeto en JSON(String)
        val jsonCaliFinal = Json.encodeToString(calificacionFinal)
        Log.d("Worker 1 JSONCaliFinal", jsonCaliFinal)

        val output = workDataOf("calisFinal_json" to jsonCaliFinal)
        return Result.success(output)
    }

}