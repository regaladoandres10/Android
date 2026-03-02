package com.example.appsice.data.remote.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class CalificacionFinal(
    val calif: Int? = null,
    val acred: String ?= null,
    val grupo: String ?= null,
    val materia: String ?= null,
    @SerialName("Observaciones")
    val observaciones: String ?= null
)
