package com.example.appsice.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appsice.data.local.entity.CalificacionUnidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalificacionUnidadDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(calificacionU: CalificacionUnidadEntity)

    @Update
    suspend fun update(calificacionU: CalificacionUnidadEntity)

    @Delete
    suspend fun delete(calificacionU: CalificacionUnidadEntity)

    @Query("SELECT * FROM calificacionUnidad WHERE id= :id")
    fun getCalificacionUnidad(id: Int): Flow<CalificacionUnidadEntity>

    @Query("SELECT * FROM calificacionUnidad ORDER BY materia ASC")
    fun getAllCalificacionUnidad(): Flow<List<CalificacionUnidadEntity>>
}