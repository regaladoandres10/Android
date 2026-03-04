package com.example.appsice.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("carga_academica")
data class CargaAcademicaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val observaciones: String,
    @ColumnInfo("calificacion13") val c13: String,
    @ColumnInfo("calificacion12") val c12: String,
    @ColumnInfo("calificacion11") val c11: String,
    @ColumnInfo("calificacion10") val c10: String,
    @ColumnInfo("calificacion9") val c9: String,
    @ColumnInfo("calificacion8") val c8: String,
    @ColumnInfo("calificacion7") val c7: String,
    @ColumnInfo("calificacion6") val c6: String,
    @ColumnInfo("calificacion5") val c5: String,
    @ColumnInfo("calificacion4") val c4: String,
    @ColumnInfo("calificacion3") val c3: String,
    @ColumnInfo("calificacion2") val c2: String,
    @ColumnInfo("calificacion1") val c1: String,
    val unidadesActivas: String,
    val materia: String,
    val grupo: String
)

