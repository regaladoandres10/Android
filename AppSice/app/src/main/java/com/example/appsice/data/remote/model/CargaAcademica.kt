package com.example.appsice.data.remote.model

import androidx.annotation.Nullable
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class CargaAcademica(
    @SerialName("Semipresencial")
    val semipresencial: String?=null,
    @SerialName("Observaciones")
    val observaciones: String?=null,
    @SerialName("Docente")
    val docente: String?=null,
    @SerialName("clvOficial")
    val clv: String?=null,
    @SerialName("Sabado")
    val sabado: String?=null,
    @SerialName("Viernes")
    val viernes: String?=null,
    @SerialName("Jueves")
    val jueves: String?=null,
    @SerialName("Miercoles")
    val miercoles: String?=null,
    @SerialName("Martes")
    val martes: String?=null,
    @SerialName("Lunes")
    val lunes: String?=null,
    @SerialName("EstadoMateria")
    val estadoMateria: String?=null,
    @SerialName("CreditosMateria")
    val creditosMateria: Int?=null,
    @SerialName("Materia")
    val materia: String?=null,
    @SerialName("Grupo")
    val grupo: String?=null
)
