package com.example.appsice.data.local.repository

import com.example.appsice.data.local.entity.CalificacionFinalEntity
import kotlinx.coroutines.flow.Flow

interface CalificacionFinalRepository {
    /**
     * Retrieve all the [UsuarioEntity] from the the given data source.
     */
    fun getAllCalisFinaltream(): Flow<List<CalificacionFinalEntity>>

    /**
     * Retrieve an usuario from the given data source that matches with the [id].
     */
    fun getCalisFinaltream(id: Int): Flow<CalificacionFinalEntity?>

    /**
     * Insert usuario in the data source
     */
    suspend fun insertCalisFinal(calificacionFinal: CalificacionFinalEntity)

    /**
     * Delete usuario from the data source
     */
    suspend fun deleteCalisFinal(calificacionFinal: CalificacionFinalEntity)

    /**
     * Update usuario in the data source
     */
    suspend fun updateCalisFinal(calificacionFinal: CalificacionFinalEntity)
}