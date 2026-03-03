package com.example.appsice.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.appsice.MarsPhotosApplication
import com.example.appsice.data.repository.SNRepository
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
        val profile = (applicationContext as SNRepository).profile()

        //Deserializar el profile
        val json = Json.encodeToString(profile)

        //Datos de salida
        val output = workDataOf("profile_json" to json)
        return Result.success(output)

    }

}
