package com.example.appsice.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("calificacionUnidad")
data class CalificacionUnidadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val observaciones: String,
    @ColumnInfo("Calificacion 13") val c13: String,
    @ColumnInfo("Calificacion 12") val c12: String,
    @ColumnInfo("Calificacion 11") val c11: String,
    @ColumnInfo("Calificacion 10") val c10: String,
    @ColumnInfo("Calificacion 9") val c9: String,
    @ColumnInfo("Calificacion 8") val c8: String,
    @ColumnInfo("Calificacion 7") val c7: String,
    @ColumnInfo("Calificacion 6") val c6: String,
    @ColumnInfo("Calificacion 5") val c5: String,
    @ColumnInfo("Calificacion 4") val c4: String,
    @ColumnInfo("Calificacion 3")  val c3: String,
    @ColumnInfo("Calificacion 2") val c2: String,
    @ColumnInfo("Calificacion 1") val c1: String,
    val unidadesActivas: String,
    val materia: String,
    val grupo: String
)
