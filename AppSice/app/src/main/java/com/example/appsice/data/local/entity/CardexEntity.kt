package com.example.appsice.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardexEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("fecha_esperada") val fecEsp: String,
    val clvMat: String,
    val clvOfiMat: String,
    val materia: String,
    @ColumnInfo("creditos")val cdts: Int,
    @ColumnInfo("calificacion")val calif: Int,
    val acred: String,
    @ColumnInfo("semestre1") val s1: String,
    @ColumnInfo("periodo1") val p1: String,
    val a1: String,
    @ColumnInfo("semestre2")val s2: String,
    @ColumnInfo("periodo2") val p2: String,
    val a2: String
)
