package com.example.appsice.data.local.repository

import com.example.appsice.data.local.entity.CalificacionUnidadEntity
import com.example.appsice.data.local.entity.CargaAcademicaEntity
import com.example.appsice.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

interface CalificacionUnidadRepository {
    /**
     * Retrieve all the [UsuarioEntity] from the the given data source.
     */
    fun getAllCalificacionUStream(): Flow<List<CalificacionUnidadEntity>>

    /**
     * Retrieve an usuario from the given data source that matches with the [id].
     */
    fun getCalificacionUStream(id: Int): Flow<CalificacionUnidadEntity?>

    /**
     * Insert usuario in the data source
     */
    suspend fun insertCalificacionU(calificacionU: CalificacionUnidadEntity)
    suspend fun insertAll(calisFinal: List<CalificacionUnidadEntity>)

    /**
     * Delete usuario from the data source
     */
    suspend fun deleteCalificacionU(calificacionU: CalificacionUnidadEntity)

    /**
     * Update usuario in the data source
     */
    suspend fun updateCalficacionU(calificacionU: CalificacionUnidadEntity)
}