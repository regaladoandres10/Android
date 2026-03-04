package com.example.appsice.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appsice.data.local.entity.CargaAcademicaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CargaAcademicaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(carga: CargaAcademicaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cargas: List<CargaAcademicaEntity>)

    @Update
    suspend fun update(carga: CargaAcademicaEntity)

    @Delete
    suspend fun delete(carga: CargaAcademicaEntity)

    @Query("SELECT * FROM carga_academica WHERE claveOficial = :clv")
    fun getCarga(clv: String): Flow<CargaAcademicaEntity>

    @Query("SELECT * FROM carga_academica ORDER BY materia ASC")
    fun getAllCarga(): Flow<List<CargaAcademicaEntity>>


}