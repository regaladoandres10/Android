package data.local.database.data.repository

import data.local.database.data.remote.api.SiceApi
import data.remote.model.CalificacionFinal
import data.remote.model.CalificacionUnidad
import data.remote.model.Cardex
import data.remote.model.CardexResponse
import data.remote.model.ProfileStudent
import data.remote.model.CargaAcademica
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json


@OptIn(InternalSerializationApi::class)
interface SNRepository {
    suspend fun acceso(m: String, p: String): String
    suspend fun profile(): ProfileStudent
    suspend fun getCargaAcademica(): List<CargaAcademica>
    suspend fun getCardex(lineamiento: Int?): List<Cardex>
    suspend fun getCaliPorUnidad(): List<CalificacionUnidad>
    suspend fun getCaliFinal(modEducativo: Int): List<CalificacionFinal>
}

@OptIn(InternalSerializationApi::class)
class NetworkSNRepository(
    private val api: SiceApi
) : SNRepository {

    override suspend fun acceso(m: String, p: String): String {

        val soapBody = """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <accesoLogin xmlns="http://tempuri.org/">
              <strMatricula>$m</strMatricula>
              <strContrasenia>$p</strContrasenia>
              <tipoUsuario>ALUMNO</tipoUsuario>
            </accesoLogin>
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()

        val response = api.accesoLogin(soapBody)
        if (!response.contains("<accesoLoginResult>")) {
            return response
        }

        //Obtenemos la respuesta
        println("SOAP RESPONSE:")
        println(response)

        //Extraer el String de la respuesta
        val result = response
            .substringAfter("<accesoLoginResult>")
            .substringBefore("</accesoLoginResult>")
            .trim()

        return result

    }

    override suspend fun profile(): ProfileStudent {
        val soapBody = """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()

        val response = api.profile(soapBody)

        println("PROFILE RESPONSE:")
        println(response)

        val json = response
            .substringAfter("<getAlumnoAcademicoWithLineamientoResult>")
            .substringBefore("</getAlumnoAcademicoWithLineamientoResult>")
            .trim()

        println("PROFILE JSON:")
        println(json)

        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString<ProfileStudent>(json)
    }

    override suspend fun getCargaAcademica(): List<CargaAcademica> {
        val soapBody = """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getCargaAcademicaByAlumno xmlns="http://tempuri.org/" />
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()

        val response = api.cargaAcademica(soapBody)

        println("CARGA RESPONSE:")
        println(response)

        val json = response
            .substringAfter("<getCargaAcademicaByAlumnoResult>")
            .substringBefore("</getCargaAcademicaByAlumnoResult>")
            .trim()

        println("CARGA JSON:")
        println(json)

        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString<List<CargaAcademica>>(json)
    }

    override suspend fun getCardex(lineamiento: Int?): List<Cardex> {
        val soapBody = """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getAllKardexConPromedioByAlumno xmlns="http://tempuri.org/">
              <aluLineamiento>$lineamiento</aluLineamiento>
            </getAllKardexConPromedioByAlumno>
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()

        val response = api.cardex(soapBody)

        println("CARDEX RESPONSE:")
        println(response)

        val json = response
            .substringAfter("<getAllKardexConPromedioByAlumnoResult>")
            .substringBefore("</getAllKardexConPromedioByAlumnoResult>")
            .trim()

        println("CARDEX JSON:")
        println(json)

         val cardex = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<CardexResponse>(json)

        return cardex.listCardex
    }

    override suspend fun getCaliPorUnidad(): List<CalificacionUnidad> {
        val soapBody = """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getCalifUnidadesByAlumno xmlns="http://tempuri.org/" />
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()

        val response = api.getCaliPorUnidad(soapBody)

        println("CALIUNIDAD RESPONSE:")
        println(response)

        val json = response
            .substringAfter("<getCalifUnidadesByAlumnoResult>")
            .substringBefore("</getCalifUnidadesByAlumnoResult>")
            .trim()

        println("CALIUNIDAD JSON:")
        println(json)

        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString<List<CalificacionUnidad>>(json)
    }

    override suspend fun getCaliFinal(modEducativo: Int): List<CalificacionFinal> {
        val soapBody = """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getAllCalifFinalByAlumnos xmlns="http://tempuri.org/">
              <bytModEducativo>$modEducativo</bytModEducativo>
            </getAllCalifFinalByAlumnos>
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()

        val response = api.getCaliFinal(soapBody)

        println("CALIFINAL RESPONSE:")
        println(response)

        val json = response
            .substringAfter("<getAllCalifFinalByAlumnosResult>")
            .substringBefore("</getAllCalifFinalByAlumnosResult>")
            .trim()

        println("CALIFINAL JSON:")
        println(json)

        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString<List<CalificacionFinal>>(json)
    }
}

