package com.example.appsice.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class Cardex (
    @SerialName("FecEsp")
    val fecEsp: String? = null, //Null
    @SerialName("ClvMat")
    val clvMat: String? = null,
    @SerialName("ClvOfiMat")
    val clvOfiMat: String? = null,
    @SerialName("Materia")
    val materia: String? = null,
    @SerialName("Cdts")
    val cdts: Int? = null,
    @SerialName("Calif")
    val calif: Int? = null,
    @SerialName("Acred")
    val acred: String? = null,
    @SerialName("S1")
    val s1: String? = null,
    @SerialName("P1")
    val p1: String? = null,
    @SerialName("A1")
    val a1: String? = null,
    @SerialName("S2")
    val s2: String? = null, //Null
    @SerialName("P2")
    val p2: String? = null, //Null
    @SerialName("A2")
    val a2: String? = null //Null
)

@InternalSerializationApi @Serializable
data class CardexResponse(
    @SerialName("lstKardex")
    val listCardex: List<Cardex>
)
