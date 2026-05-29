package data.local.repository

import data.local.dao.CardexDao
import data.local.dao.CargaAcademicaDao
import data.local.entity.CardexEntity
import data.local.entity.CargaAcademicaEntity
import kotlinx.coroutines.flow.Flow

class OfflineCardexRepository(private val cardexDao: CardexDao) : CardexRepository {
    override fun getAllCardexStream(): Flow<List<CardexEntity>> = cardexDao.getAllCardex()

    override fun getCardexStream(clv: String): Flow<CardexEntity?> = cardexDao.getCardex(clv)
    override suspend fun insertAll(cardexs: List<CardexEntity>) = cardexDao.insertAll(cardexs)

    override suspend fun insertCardex(cardex: CardexEntity) = cardexDao.insert(cardex)

    override suspend fun deleteCardex(cardex: CardexEntity) = cardexDao.delete(cardex)

    override suspend fun updateCardex(cardex: CardexEntity) = cardexDao.update(cardex)
    override suspend fun deleteAll() { cardexDao.deleteAll() }
}