package data.repository

import data.remote.datasource.RemoteDataSource
import data.remote.model.CalificacionFinal
import data.remote.model.CalificacionUnidad
import data.remote.model.Cardex
import data.remote.model.CardexResponse
import data.remote.model.CargaAcademica
import data.remote.model.ProfileStudent
import data.remote.util.SoapBuilder
import data.remote.util.SoapParser
import kotlinx.serialization.InternalSerializationApi

/**
 * Este archivo nos permite hacer las llamadas HTTP y convertir el XML a JSON
 */

@OptIn(InternalSerializationApi::class)
class NetworkSNRepository(
    private val remote: RemoteDataSource
) : SNRepository {

    //Login
    override suspend fun acceso(
        m: String,
        p: String
    ): String {
        //Obtenemos el XML y agregamos las credenciales para inciar sesión
        val xmlRequest = SoapBuilder.acceso(m, p)
        //Aqui hacemos la petición HTTP con el XML y obtenemos el XML
        return remote.acceso(xmlRequest)
    }

    //Perfil
    override suspend fun profile(): ProfileStudent {
        //Hacemos la peticion HTTP
        val xml = remote.profile(SoapBuilder.profile())
        //Extraemos el JSON del XML
        val json = SoapParser.extractJson(xml, "getAlumnoAcademicoWithLineamientoResult")
        //Convertimos el JSON a un objeto de tipo ProfileStudent
        return SoapParser.parseJson<ProfileStudent>(json)
    }

    //Carga académica
    override suspend fun getCargaAcademica() : List<CargaAcademica> {
        //Hacemos la peticion HTTP
        val xml = remote.cargaAcademica(SoapBuilder.getCargaAcademica())
        //Extraemos el JSON del XML
        val json = SoapParser
                .extractJson(xml, "getCargaAcademicaByAlumnoResult")

        //Convertimos el JSON a un objeto de tipo CargaAcademica
        return SoapParser.parseJson(json)

    }

    //Cardex
    override suspend fun getCargaCardex(lineamiento: Int): List<Cardex> {
        //Hacemos la peticion HTTP
        val xml = remote.cardex(SoapBuilder.getCargaCardex(lineamiento))
        //Extraemos el JSON del XML
        val json = SoapParser
            .extractJson(xml, "getAllKardexConPromedioByAlumnoResult")
        //Convertimos el JSON a un objeto de tipo Cardex
        val response = SoapParser.parseJson<CardexResponse>(json)

        //Retornamos la lista de Cardex
        return response.listCardex
    }

    //Calificación unidad
    override suspend fun getCaliPorUnidad() : List<CalificacionUnidad> {
        val xml = remote.caliUnidad(SoapBuilder.getCaliPorUnidad())
        val json = SoapParser
            .extractJson(xml, "getCalifUnidadesByAlumnoResult")

        return SoapParser.parseJson(json)
    }

    //Calificación final
    override suspend fun getCaliFinal(modEducativo: Int) : List<CalificacionFinal> {
        val xml = remote.caliFinal(SoapBuilder.getCaliFinal(modEducativo))

        val json = SoapParser
            .extractJson(xml, "getAllCalifFinalByAlumnosResult")

        return SoapParser.parseJson(json)
    }
}