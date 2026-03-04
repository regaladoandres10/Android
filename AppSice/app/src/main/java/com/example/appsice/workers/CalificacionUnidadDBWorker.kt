package com.example.appsice.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appsice.SNApplication
import com.example.appsice.data.local.entity.toEntity
import com.example.appsice.data.remote.model.CalificacionUnidad
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json

class CalificacionUnidadDBWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        //Obtenemos el JSON de Calificacion Unidad del primer worker
        val jsonCaliUnidad = inputData.getString("calisUnidad_json") ?: return Result.failure()

        //Deserializamos el JSON y lo asignamos al DataClass [CalificacionUnidad]
        val caliUnidad = Json.decodeFromString<List<CalificacionUnidad>>(jsonCaliUnidad)
        val container = (applicationContext as SNApplication).container
        val caliUnidadRepository = container.calificacionUnidadRepository

        //Agregamos la lista de Calificacion Unidad al metodo de insertAll para agregarlo a la Base de datos
        caliUnidadRepository.insertAll(
            caliUnidad.map {
                it.toEntity()
            }
        )

        //Asignamos la CalificacionUnidad a CalificacionUnidadEntity
        Log.i("Worker2 caliUnidad", "Guardando datos en la base de datos CalisUnidad")
        Log.d("WORKER_CAL_UNIDAD",  "Cantidad ${caliUnidad.size}")
        return Result.success()
    }

}