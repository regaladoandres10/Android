package com.example.appsice.data.local.repository

import com.example.appsice.data.local.entity.CardexEntity
import com.example.appsice.data.local.entity.CargaAcademicaEntity
import com.example.appsice.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

interface CardexRepository {
    /**
     * Retrieve all the [UsuarioEntity] from the the given data source.
     */
    fun getAllCardexStream(): Flow<List<CardexEntity>>

    /**
     * Retrieve an usuario from the given data source that matches with the [id].
     */
    fun getCardexStream(id: Int): Flow<CardexEntity?>

    suspend fun insertAll(cardexs: List<CardexEntity>)

    /**
     * Insert usuario in the data source
     */
    suspend fun insertCardex(cardex: CardexEntity)

    /**
     * Delete usuario from the data source
     */
    suspend fun deleteCardex(cardex: CardexEntity)

    /**
     * Update usuario in the data source
     */
    suspend fun updateCardex(cardex: CardexEntity)
}