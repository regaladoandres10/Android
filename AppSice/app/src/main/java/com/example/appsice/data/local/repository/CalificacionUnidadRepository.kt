package com.example.appsice.data.local.repository

import com.example.appsice.data.local.entity.CaliificacionUnidadEntity
import com.example.appsice.data.local.entity.CardexEntity
import com.example.appsice.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

interface CalificacionUnidadRepository {
    /**
     * Retrieve all the [UsuarioEntity] from the the given data source.
     */
    fun getAllCalificacionUStream(): Flow<List<CaliificacionUnidadEntity>>

    /**
     * Retrieve an usuario from the given data source that matches with the [id].
     */
    fun getCalificacionUStream(id: Int): Flow<CaliificacionUnidadEntity?>

    /**
     * Insert usuario in the data source
     */
    suspend fun insertCalificacionU(calificacionU: CaliificacionUnidadEntity)

    /**
     * Delete usuario from the data source
     */
    suspend fun deleteCalificacionU(calificacionU: CaliificacionUnidadEntity)

    /**
     * Update usuario in the data source
     */
    suspend fun updateCalficacionU(calificacionU: CaliificacionUnidadEntity)
}