package data.local.repository

import data.local.dao.CalificacionUnidadDao
import data.local.entity.CalificacionUnidadEntity
import kotlinx.coroutines.flow.Flow

class OfflineCalificacionURepository(private val calificacionUDao: CalificacionUnidadDao): CalificacionUnidadRepository {
    override fun getAllCalificacionUStream(): Flow<List<CalificacionUnidadEntity>> = calificacionUDao.getAllCalificacionUnidad()

    override fun getCalificacionUStream(id: Int): Flow<CalificacionUnidadEntity?> = calificacionUDao.getCalificacionUnidad(id)

    override suspend fun insertCalificacionU(calificacionU: CalificacionUnidadEntity) = calificacionUDao.insert(calificacionU)
    override suspend fun insertAll(calisFinal: List<CalificacionUnidadEntity>) = calificacionUDao.insertAll(calisFinal)
    override suspend fun getAllCalificacionUnidadList(): List<CalificacionUnidadEntity> =
        calificacionUDao.getAllCalificacionUnidadList()

    override suspend fun deleteCalificacionU(calificacionU: CalificacionUnidadEntity) = calificacionUDao.delete(calificacionU)

    override suspend fun updateCalficacionU(calificacionU: CalificacionUnidadEntity) = calificacionUDao.update(calificacionU)
    override suspend fun deleteAll() {
        calificacionUDao.deleteAll()
    }

}