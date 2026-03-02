package com.example.appsice.data.local.repository

import com.example.appsice.data.local.dao.CalificacionUnidadDao
import com.example.appsice.data.local.entity.CaliificacionUnidadEntity
import kotlinx.coroutines.flow.Flow


class OfflineCalificacionURepository(private val calificacionUDao: CalificacionUnidadDao): CalificacionUnidadRepository {
    override fun getAllCalificacionUStream(): Flow<List<CaliificacionUnidadEntity>> = calificacionUDao.getAllCalificacionUnidad()

    override fun getCalificacionUStream(id: Int): Flow<CaliificacionUnidadEntity?> = calificacionUDao.getCalificacionUnidad(id)

    override suspend fun insertCalificacionU(calificacionU: CaliificacionUnidadEntity) = calificacionUDao.insert(calificacionU)

    override suspend fun deleteCalificacionU(calificacionU: CaliificacionUnidadEntity) = calificacionUDao.delete(calificacionU)

    override suspend fun updateCalficacionU(calificacionU: CaliificacionUnidadEntity) = calificacionUDao.update(calificacionU)

}