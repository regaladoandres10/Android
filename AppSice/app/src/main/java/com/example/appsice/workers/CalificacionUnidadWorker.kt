package com.example.appsice.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.appsice.SNApplication
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CalificacionUnidadWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        val container = (applicationContext as SNApplication).container
        val repository = container.snRepository

        val calificacionUnidad = repository.getCaliPorUnidad()

        //Convertimos el objeto de CalificacionUnidad a Json(String)
        val jsonCaliUnidad = Json.encodeToString(calificacionUnidad)
        Log.d("Worker1 JSONCaliUnidad", jsonCaliUnidad)

        //Mandamos el JSON al segundo worker
        val output = workDataOf("calisUnidad_json" to jsonCaliUnidad)
        return Result.success(output)
    }

}