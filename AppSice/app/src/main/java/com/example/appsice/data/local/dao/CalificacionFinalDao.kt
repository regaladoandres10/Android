package com.example.appsice.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appsice.data.local.entity.CalificacionFinalEntity
import com.example.appsice.data.local.entity.CalificacionUnidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalificacionFinalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(calificacionFinal: CalificacionFinalEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(calisFinal: List<CalificacionFinalEntity>)
    @Update
    suspend fun update(calificacionFinal: CalificacionFinalEntity)

    @Delete
    suspend fun delete(calificacionFinal: CalificacionFinalEntity)

    @Query("SELECT * FROM calificacion_final WHERE id = :id")
    fun getCalificacionFinal(id: Int): Flow<CalificacionFinalEntity>

    @Query("SELECT * FROM calificacion_final ORDER BY materia ASC")
    fun getAllCalisFinal(): Flow<List<CalificacionFinalEntity>>
}