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

class CardexWorker(
    ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx, params) {
    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        val container = (applicationContext as SNApplication).container
        val repository = container.snRepository

        val cardex = repository.getCargaCardex(3)
        val jsonCardex = Json.encodeToString(cardex)
        Log.d("Worker1 JSONCardex", jsonCardex)

        val output = workDataOf("cardex_json" to jsonCardex)
        return Result.success(output)
    }

}