package com.example.appsice.data.local.repository

import com.example.appsice.data.local.entity.CargaAcademicaEntity
import com.example.appsice.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

interface CargaAcademicaRepository {
    /**
     * Retrieve all the [UsuarioEntity] from the the given data source.
     */
    fun getAllCargaStream(): Flow<List<CargaAcademicaEntity>>

    /**
     * Retrieve an usuario from the given data source that matches with the [id].
     */
    fun getCargaStream(id: Int): Flow<CargaAcademicaEntity?>

    /**
     * Insert usuario in the data source
     */
    suspend fun insertCarga(carga: CargaAcademicaEntity)

    /**
     * Delete usuario from the data source
     */
    suspend fun deleteCarga(carga: CargaAcademicaEntity)

    /**
     * Update usuario in the data source
     */
    suspend fun updateCarga(carga: CargaAcademicaEntity)
}