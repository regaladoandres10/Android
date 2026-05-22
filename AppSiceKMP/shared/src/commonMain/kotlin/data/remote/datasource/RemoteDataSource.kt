package data.remote.datasource

import data.remote.api.SICENETService

/**
 * Este archivo solo hace llamadas HTTP o ejecuta petioiones
 * y devuelve el XML
 */

class RemoteDataSource(
    private val api:
    SICENETService
) {
    //Mandamos llamar el login desde la api
    suspend fun acceso(xml: String): String {
        return api.acceso(xml)
    }

    suspend fun profile(xml: String): String {
        return api.profile(xml)
    }

    suspend fun cargaAcademica(xml: String) = api.cargaAcademica(xml)

    suspend fun cardex(xml: String) = api.getKardex(xml)

    suspend fun caliUnidad(xml: String) = api.getCaliPorUnidad(xml)

    suspend fun caliFinal(xml: String) = api.getCaliFinal(xml)
}