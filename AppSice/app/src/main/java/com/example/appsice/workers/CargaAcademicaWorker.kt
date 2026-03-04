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

class CargaAcademicaWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params){
    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        val container = (applicationContext as SNApplication).container
        val repository = container.snRepository

        val carga = repository.getCargaAcademica()
        //Deserializar la carga academica
        val jsonCarga = Json.encodeToString(carga)
        Log.d("Worker1 JSONCarga", jsonCarga)

        val output = workDataOf("carga_json" to jsonCarga)
        return Result.success(output)
    }

}