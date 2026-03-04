package com.example.appsice.data.remote.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class CalificacionUnidad(
    @SerialName("Observaciones")
    val observaciones: String ?= null,
    @SerialName("C13")
    val c13: String ?= null,
    @SerialName("C12")
    val c12: String ?= null,
    @SerialName("C11")
    val c11: String ?= null,
    @SerialName("C10")
    val c10: String ?= null,
    @SerialName("C9")
    val c9: String ?= null,
    @SerialName("C8")
    val c8: String ?= null,
    @SerialName("C7")
    val c7: String ?= null,
    @SerialName("C6")
    val c6: String ?= null,
    @SerialName("C5")
    val c5: String ?= null,
    @SerialName("C4")
    val c4: String ?= null,
    @SerialName("C3")
    val c3: String ?= null,
    @SerialName("C2")
    val c2: String ?= null,
    @SerialName("C1")
    val c1: String ?= null,
    @SerialName("UnidadesActivas")
    val unidadesActivas: String ?= null,
    @SerialName("Materia")
    val materia: String ?= null,
    @SerialName("Grupo")
    val grupo: String ?= null
)
