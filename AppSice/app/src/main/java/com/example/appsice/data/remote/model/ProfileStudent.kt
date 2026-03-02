package com.example.appsice.data.remote.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable

data class ProfileStudent (
    @SerialName("fechaReins")
    val fechaReins: String? = null,
    @SerialName(value = "modEducativo")
    val modEducativo: Int? = null,
    @SerialName(value = "adeudo")
    val adeudo: Boolean? = null,
    @SerialName(value = "urlFoto")
    val urlFoto: String? = null,
    @SerialName(value = "adeudoDescripcion")
    val adeudoDescripcion: String? = null,
    @SerialName(value = "inscrito")
    val inscrito: Boolean? = null,
    @SerialName(value = "estatus")
    val estatus: String? = null,
    @SerialName(value = "semActual")
    val semActual: Int? = null,
    @SerialName(value = "cdtosAcumulados")
    val cdtosAcumulados: Int? = null,
    @SerialName(value = "cdtosActuales")
    val cdtosActuales: Int? = null,
    @SerialName(value = "especialidad")
    val especialidad: String? = null,
    @SerialName(value = "carrera")
    val carrera: String? = null,
    @SerialName(value = "lineamiento")
    val lineamiento: Int? = null,
    @SerialName(value = "nombre")
    val nombre: String? = null,
    @SerialName(value = "matricula")
    val matricula: String
) {
}
