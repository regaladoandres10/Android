package com.example.appsice.workers

import android.content.Context
import android.content.ContextParams
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appsice.SNApplication
import com.example.appsice.data.local.entity.toEntity
import com.example.appsice.data.remote.model.Cardex
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json

class CardexDBWorker(
    ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx,params) {
    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        //Obtenemos el json de cardex
        val jsonCardex = inputData.getString("cardex_json") ?: return Result.failure()

        //Deserializamos el json y lo asignamos a Data Class [Cardex]
        val cardex = Json.decodeFromString<List<Cardex>>(jsonCardex)
        val container = (applicationContext as SNApplication).container
        val cardexRepository = container.cardexRepository

        //Agregamos la lista deserializada de Cardex al metodo insertAll del repository
        cardexRepository.insertAll(
            cardex.map {
                it.toEntity()
            }
        )
        Log.i("Worker2", "Guardando datos en base de datos cardex")
        return Result.success()

    }

}