package com.example.appsice.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appsice.SNApplication
import com.example.appsice.data.local.entity.toEntity
import com.example.appsice.data.remote.model.CargaAcademica
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json

class CargaAcademicaDBWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params){
    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        //Obtenemos el json de carga del worker
        val jsonCarga = inputData.getString("carga_json") ?: return Result.failure()

        //Deserializamos el json y lo asignamos al Data Class [CargaAcademica]
        val cargaAcademica = Json.decodeFromString<List<CargaAcademica>>(jsonCarga)
        val container = (applicationContext as SNApplication).container
        val cargaRepository = container.cargaAcademicaRepository

        //Agregamos la lista de Carga Academica al metodo insertAll para
        cargaRepository.insertAll(
            cargaAcademica.map {
                it.toEntity()
            }
        )

        //Asignamos la CargaAcademica a CargaAcademicaEntity
        Log.i("Worker2", "Guardando datos en base de datos de la carga academica")
        return Result.success()
    }

}