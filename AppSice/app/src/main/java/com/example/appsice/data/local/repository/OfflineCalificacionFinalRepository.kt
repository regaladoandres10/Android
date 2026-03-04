package com.example.appsice.data.local.repository

import com.example.appsice.data.local.dao.CalificacionFinalDao
import com.example.appsice.data.local.entity.CalificacionFinalEntity
import kotlinx.coroutines.flow.Flow

class OfflineCalificacionFinalRepository(private val calisFinalDao: CalificacionFinalDao) : CalificacionFinalRepository {
    override fun getAllCalisFinaltream(): Flow<List<CalificacionFinalEntity>> = calisFinalDao.getAllCalisFinal()

    override fun getCalisFinaltream(id: Int): Flow<CalificacionFinalEntity?> = calisFinalDao.getCalificacionFinal(id)

    override suspend fun insertCalisFinal(calificacionFinal: CalificacionFinalEntity) = calisFinalDao.insert(calificacionFinal)
    override suspend fun insertAll(calisFinal: List<CalificacionFinalEntity>) = calisFinalDao.insertAll(calisFinal)


    override suspend fun deleteCalisFinal(calificacionFinal: CalificacionFinalEntity) = calisFinalDao.delete(calificacionFinal)

    override suspend fun updateCalisFinal(calificacionFinal: CalificacionFinalEntity) = calisFinalDao.update(calificacionFinal)

}