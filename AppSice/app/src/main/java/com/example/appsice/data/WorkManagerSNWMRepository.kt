package com.example.appsice.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.appsice.workers.CardexDBWorker
import com.example.appsice.workers.CardexWorker
import com.example.appsice.workers.CargaAcademicaDBWorker
import com.example.appsice.workers.CargaAcademicaWorker
import com.example.appsice.workers.LoginDBWorker
import com.example.appsice.workers.LoginWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class WorkManagerSNWMRepository(ctx: Context): SNWMRepository {

    private val workManager = WorkManager.getInstance(ctx)
    override val logintWorkInfo: Flow<WorkInfo?> =
        workManager
            .getWorkInfosByTagFlow("LOGIN_WORK")
            .map { it.firstOrNull() }

    override val cargaWorkInfo: Flow<WorkInfo?> =
        workManager
            .getWorkInfosByTagFlow("CARGA_WORK")
            .map { it.firstOrNull() }

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

        //Creamos el Worker 2 -> Para almacenar en la base de datos de UsuarioEntity
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
    override fun cargaAcademica() {
        //Creamos el constraint para saber si hay internet
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //Creamos el Worker 1 (CargaAcademicaWorker) -> Llama a la API de retrofit.
        val cargaWorkRequest =
            //Se ejecuta una sola vez el worker
            OneTimeWorkRequestBuilder<CargaAcademicaWorker>()
                .setConstraints(constraints)
                .addTag("CARGA_WORK")
                .build()

        //Creamos el Worker 2 -> Para almacenar en la base de datos de CargaAcademicaEntity
        val saveWorkerDB =
            OneTimeWorkRequestBuilder<CargaAcademicaDBWorker>()
                .build()

        workManager.beginUniqueWork(
            "SYNC_CARGA_WORK",
            ExistingWorkPolicy.REPLACE, //Si se repite el proceso 2 veces solo se hace 1
            cargaWorkRequest //Mandamos llamar el primer trabajo
        )
            .then(saveWorkerDB) //Mandamos llamar o encolar el segundo trabajo
            .enqueue() //Encolamos el segundo trabajo
    }

    override fun cardex() {
        //Creamos el constraint para saber si hay internet
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //Creamos el Worker 1 (CardexWorker) -> Llama a la API de retrofit.
        val cardexWorkRequest =
            //Se ejecuta una sola vez el worker
            OneTimeWorkRequestBuilder<CardexWorker>()
                .setConstraints(constraints)
                .addTag("CARDEX_WORK")
                .build()

        //Creamos el Worker 2 -> Para almacenar en la base de datos de CardexEntity
        val saveWorkerDB =
            OneTimeWorkRequestBuilder<CardexDBWorker>()
                .build()

        workManager.beginUniqueWork(
            "SYNC_CARDEX_WORK",
            ExistingWorkPolicy.REPLACE, //Si se repite el proceso 2 veces solo se hace 1
            cardexWorkRequest //Mandamos llamar el primer trabajo
        )
            .then(saveWorkerDB) //Mandamos llamar o encolar el segundo trabajo
            .enqueue() //Encolamos el segundo trabajo
    }



}