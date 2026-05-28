package data.local.database.data.repository

import data.local.database.data.remote.api.SiceApi


interface SNRepository {
    suspend fun acceso(m: String, p: String): String
}

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

        println("SOAP RESPONSE:")
        println(response)

        //Extraer el SOAP
        val result = response
            .substringAfter("<accesoLoginResult>")
            .substringBefore("</accesoLoginResult>")
            .trim()

        return result

    }

}