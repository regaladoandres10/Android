package com.example.appsice.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.appsice.workers.LoginDBWorker
import com.example.appsice.workers.LoginWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class WorkManagerSNWMRepository(ctx: Context): SNWMRepository {

    private val workManager = WorkManager.getInstance(ctx)
    override val outputWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("LOGIN_WORK")
            .asFlow()
            .mapNotNull {
                if (it.isNotEmpty()) it.first() else null
        }

    /*
    override fun login(m: String, p: String) {

    }
     */

    override fun profile() {
        //Creamos el constraint para saber si hay internet
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //Creamos el Worker 1 (LoginWorker) -> Llama a la API de retrofit.
        val loginWorkRequest =
            //Se ejecuta una sola vez el worker
            OneTimeWorkRequestBuilder<LoginWorker>()
                .setConstraints(constraints)
                .addTag("LOGIN_WORK")
                .build()

        //Creamos el Worker 2 -> Para almacenar en la base de datos
        val saveWorker =
            OneTimeWorkRequestBuilder<LoginDBWorker>()
                .build()

        //Worker unico
        workManager.beginUniqueWork(
            "SYNC_LOGIN_WORK",
            ExistingWorkPolicy.REPLACE, //Si se repite el proceso 2 veces solo se hace 1
            loginWorkRequest //Mandamos llamar el primer trabajo
        )
            .then(saveWorker) //Mandamos llamar o encolar el segundo trabajo
            .enqueue() //Encolamos el segundo trabajo

    }
    /*
    override fun cargaAcademica() {
        //TODO("Not yet implemented")
    }
     */
}