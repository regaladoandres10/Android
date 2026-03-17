package com.example.appsice.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appsice.SNApplication
import com.example.appsice.data.local.entity.toEntity
import com.example.appsice.data.remote.model.ProfileStudent
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json

class LoginDBWorker (ctx: Context, params: WorkerParameters) :
    CoroutineWorker (ctx, params) {
    @OptIn(InternalSerializationApi::class)
    override suspend fun doWork(): Result {
        //Obtener el json de inputData
        val json = inputData.getString("profile_json")
           ?: return Result.failure()

        //Deserializamos el json y lo asignamos al Data Class de [ProfileStudent]
        val profile = Json.decodeFromString<ProfileStudent>(json)
        val container = (applicationContext as SNApplication).container
        val usuarioRepository = container.usuarioRepository

        //Convertimos la deserialización de profile a Entity
        //Lo asignamos en la base de datos en la Entity de UsuarioEntity
        usuarioRepository.insertUsuario(profile.toEntity())
        Log.i("Worker2", "Guardando datos en base de datos")

        return Result.success()
    }
}
