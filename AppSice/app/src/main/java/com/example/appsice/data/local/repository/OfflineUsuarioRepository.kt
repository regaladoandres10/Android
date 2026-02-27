package com.example.appsice.data.local.repository

import com.example.appsice.data.local.dao.UsuarioDao
import com.example.appsice.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

class OfflineUsuarioRepository(private val usuarioDao: UsuarioDao) : UsuarioRepository {
    override fun getAllUsuarioStream(): Flow<List<UsuarioEntity>> = usuarioDao.getAllUsuario()

    override fun getUsuarioStream(id: Int): Flow<UsuarioEntity?> =  usuarioDao.getUsuario(id)

    override suspend fun insertUsuario(usuario: UsuarioEntity) = usuarioDao.insert(usuario)

    override suspend fun deleteUsuario(usuario: UsuarioEntity) = usuarioDao.delete(usuario)

    override suspend fun updateUsuario(usuario: UsuarioEntity) = usuarioDao.update(usuario)

}