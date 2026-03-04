package com.example.appsice.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appsice.data.local.entity.CardexEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardexDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cadex: CardexEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cargas: List<CardexEntity>)

    @Update
    suspend fun update(cardex: CardexEntity)

    @Delete
    suspend fun delete(cardex: CardexEntity)

    //Get one cardex
    @Query("SELECT * FROM cardex WHERE id = :id")
    fun getCardex(id: Int): Flow<CardexEntity>

    //Get all
    @Query("SELECT * FROM cardex ORDER BY materia ASC")
    fun getAllCardex(): Flow<List<CardexEntity>>
}