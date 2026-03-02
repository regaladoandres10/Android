package com.example.appsice.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appsice.data.local.entity.CaliificacionUnidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalificacionUnidadDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(calificacionU: CaliificacionUnidadEntity)

    @Update
    suspend fun update(calificacionU: CaliificacionUnidadEntity)

    @Delete
    suspend fun delete(calificacionU: CaliificacionUnidadEntity)

    @Query("SELECT * FROM calificacionUnidad WHERE id= :id")
    fun getCalificacionUnidad(id: Int): Flow<CaliificacionUnidadEntity>

    @Query("SELECT * FROM calificacionUnidad ORDER BY materia ASC")
    fun getAllCalificacionUnidad(): Flow<List<CaliificacionUnidadEntity>>
}