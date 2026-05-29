package data.local.database.data.repository

import data.local.database.data.remote.api.SiceApi
import data.remote.model.ProfileStudent
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json


@OptIn(InternalSerializationApi::class)
interface SNRepository {
    suspend fun acceso(m: String, p: String): String
    suspend fun profile(): ProfileStudent
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
}

