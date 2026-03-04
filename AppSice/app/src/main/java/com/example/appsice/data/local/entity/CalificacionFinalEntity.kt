package com.example.appsice.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("calificacion_final")
data class CalificacionFinalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("calificacion") val calif: Int,
    @ColumnInfo("acreditado") val acred: String,
    val grupo: String,
    val materia: String,
    val observaciones: String
)
