package com.example.appsice.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("carga_academica")
data class CargaAcademicaEntity(
    val semipresencial: String,
    val observaciones: String,
    val docente: String,
    @PrimaryKey
    @ColumnInfo("claveOficial") val clvOficial: String,
    val sabado: String,
    val viernes: String,
    val jueves: String,
    val miercoles: String,
    val martes: String,
    val lunes: String,
    val estadoMateria: String,
    val creditosMateria: Int,
    val materia: String,
    val grupo: String
)

