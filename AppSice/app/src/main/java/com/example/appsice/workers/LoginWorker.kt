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

class LoginWorker(
    ctx: Context,
    params: WorkerParameters
)
    : CoroutineWorker(ctx,params){

    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        val container = (applicationContext as SNApplication).container
        val repository = container.snRepository

        val profile = repository.profile()


        val json = Json.encodeToString(profile)
        Log.d("Worker1 JSONProfile", json)
        //Datos de salida
        val output = workDataOf("profile_json" to json)

        return Result.success(output)
    }
}
