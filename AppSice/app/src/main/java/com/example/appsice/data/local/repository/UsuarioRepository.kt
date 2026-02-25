package com.example.appsice.data.local.repository

import com.example.appsice.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Usuario] from a given data source.
 */
interface UsuarioRepository {
    /**
     * Retrieve all the [UsuarioEntity] from the the given data source.
     */
    fun getAllUsuarioStream(): Flow<List<UsuarioEntity>>

    /**
     * Retrieve an usuario from the given data source that matches with the [id].
     */
    fun getUsuarioStream(id: Int): Flow<UsuarioEntity>

    /**
     * Insert usuario in the data source
     */
    suspend fun insertUsuario(usuarioEntity: UsuarioEntity)

    /**
     * Delete usuario from the data source
     */
    suspend fun deleteUsuario(usuarioEntity: UsuarioEntity)

    /**
     * Update usuario in the data source
     */
    suspend fun updateUsuario(usuarioEntity: UsuarioEntity)

}