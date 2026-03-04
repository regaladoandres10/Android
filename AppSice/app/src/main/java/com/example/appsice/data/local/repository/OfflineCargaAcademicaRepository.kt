package com.example.appsice.data.local.repository

import com.example.appsice.data.local.dao.CargaAcademicaDao
import com.example.appsice.data.local.entity.CargaAcademicaEntity
import kotlinx.coroutines.flow.Flow

class OfflineCargaAcademicaRepository(private val cargaDao: CargaAcademicaDao) : CargaAcademicaRepository {
    override fun getAllCargaStream(): Flow<List<CargaAcademicaEntity>> = cargaDao.getAllCarga()

    override fun getCargaStream(id: Int): Flow<CargaAcademicaEntity?> = cargaDao.getCarga(id)

    override suspend fun insertCarga(carga: CargaAcademicaEntity) = cargaDao.insert(carga)

    override suspend fun deleteCarga(carga: CargaAcademicaEntity) = cargaDao.delete(carga)

    override suspend fun updateCarga(carga: CargaAcademicaEntity) = cargaDao.update(carga)
}