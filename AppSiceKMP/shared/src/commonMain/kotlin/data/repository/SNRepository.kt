package data.repository

import data.remote.model.CalificacionFinal
import data.remote.model.CalificacionUnidad
import data.remote.model.Cardex
import data.remote.model.CargaAcademica
import data.remote.model.ProfileStudent
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
interface SNRepository {
    /** Fetches list of MarsPhoto from marsApi */
    suspend fun acceso(m: String, p: String): String
    suspend fun profile(): ProfileStudent
    suspend fun getCargaAcademica(): List<CargaAcademica>
    suspend fun getCargaCardex(lineamiento: Int): List<Cardex>
    suspend fun getCaliPorUnidad(): List<CalificacionUnidad>
    suspend fun getCaliFinal(modEducativo: Int): List<CalificacionFinal>
}