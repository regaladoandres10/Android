package com.example.appsice.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appsice.SNApplication
import com.example.appsice.data.local.entity.toEntity
import com.example.appsice.data.remote.model.CalificacionFinal
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json

class CalificacionFinalDBWorker(
    ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx, params) {
    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        //Obtenemos el JSON de Calificacion Final del primer worker
        val jsonCaliFinal = inputData.getString("calisFinal_json") ?: return Result.failure()

        //Deserializamos el JSON y lo asignamos a CalificacionFinal en forma de lista
        val calisFinal = Json.decodeFromString<List<CalificacionFinal>>(jsonCaliFinal)
        val container = (applicationContext as SNApplication).container
        val caliFinalRepository = container.calificacionFinalRepository

        //Agregamos la lista de calificaciones finales al metodo de insertAll dentro de la base de datos
        caliFinalRepository.insertAll(
            calisFinal.map {
                it.toEntity()
            }
        )

        Log.i("Worker2 califinal", "Guardando datos en la base de datos CalisFinal")
        return Result.success()

    }

}