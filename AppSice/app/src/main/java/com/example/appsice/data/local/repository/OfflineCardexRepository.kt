package com.example.appsice.data.local.repository

import com.example.appsice.data.local.dao.CardexDao
import com.example.appsice.data.local.entity.CardexEntity
import kotlinx.coroutines.flow.Flow

class OfflineCardexRepository(private val cardexDao: CardexDao) : CardexRepository {
    override fun getAllCardexStream(): Flow<List<CardexEntity>> = cardexDao.getAllCardex()

    override fun getCardexStream(id: Int): Flow<CardexEntity?> = cardexDao.getCardex(id)
    override suspend fun insertAll(cardexs: List<CardexEntity>) = cardexDao.insertAll(cardexs)

    override suspend fun insertCardex(cardex: CardexEntity) = cardexDao.insert(cardex)

    override suspend fun deleteCardex(cardex: CardexEntity) = cardexDao.delete(cardex)

    override suspend fun updateCardex(cardex: CardexEntity) = cardexDao.update(cardex)

}