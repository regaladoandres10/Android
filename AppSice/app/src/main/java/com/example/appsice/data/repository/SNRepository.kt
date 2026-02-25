/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:OptIn(InternalSerializationApi::class)

package com.example.appsice.data.repository

import android.util.Log
import com.example.appsice.data.remote.model.CalifacionUnidad
import com.example.appsice.data.remote.model.CalificacionFinal
import com.example.appsice.data.remote.model.Cardex
import com.example.appsice.data.remote.model.CardexResponse
import com.example.appsice.data.remote.model.CargaAcademica
import com.example.appsice.data.remote.model.ProfileStudent
import com.example.appsice.data.remote.model.Usuario
import com.example.appsice.data.remote.SICENETWService
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Repository that fetch mars photos list from marsApi.
 */

interface SNRepository {
    /** Fetches list of MarsPhoto from marsApi */
    suspend fun acceso(m: String, p: String): String
    suspend fun accesoObjeto(m: String, p: String): Usuario
    suspend fun profile(): ProfileStudent
    suspend fun getCargaAcademica(): List<CargaAcademica>
    suspend fun getCargaCardex(lineamiento: Int): List<Cardex>
    suspend fun getCaliPorUnidad(): List<CalifacionUnidad>
    suspend fun getCaliFinal(modEducativo: Int): List<CalificacionFinal>
}

/*
class DBLocalSNRepository(val apiDB : Any):SNRepository {
    override suspend fun acceso(m: String, p: String): String {
        //TODO("Not yet implemented")
        //Reviso en base de datos
        //Preparar Room

        //apiDB.acceso( Usuario(matricula = m) )

        return ""

    }

    override suspend fun accesoObjeto(m: String, p: String): Usuario {

        //Tengo  que ir a Room
        return Usuario(matricula = "")
    }

    /*
    override suspend fun profile(): ProfileStudent {
        //TODO("Not yet implemented")
        return ProfileStudent("")
    }
     */
}
 */

/**
 * Network Implementation of Repository that fetch mars photos list from marsApi.
 */

class NetworSNRepository(
    private val snApiService: SICENETWService
) : SNRepository {
    /** Fetches list of MarsPhoto from marsApi*/
    //private val api = NetworkModule.provideApi(context)
    override suspend fun acceso(m: String, p: String): String {

        val bodyacceso =
            """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <accesoLogin xmlns="http://tempuri.org/">
              <strMatricula>${m}</strMatricula>
              <strContrasenia>${p}</strContrasenia>   
              <tipoUsuario>ALUMNO</tipoUsuario>
            </accesoLogin>
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()

        //callHTTPS()

        val requestBody = bodyacceso.toRequestBody("text/xml; charset=utf-8".toMediaType())
        //Arma el XML y llama el servicio
        //Se guarda la cookie
        val respone = snApiService.acceso(requestBody )

        //Respuesta en XML
        val xml = respone.string()

        Log.d("RXML", xml )
       /* Log.d("RXML", res.body?.accesoLoginResponse?.accesoLoginResult.toString() )

        return res.body?.accesoLoginResponse?.accesoLoginResult.toString()*/
        /*Log.d("RXML", res.message() )
        return res.message()*/
        return xml
    }

    override suspend fun accesoObjeto(m: String, p: String): Usuario {
        //TODO("Not yet implemented")
        return Usuario(matricula = "")
    }

    override suspend fun profile(): ProfileStudent {
        val bodyProfile =
            """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                       xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
                       xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
        <soap:Body>
        <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
        </soap:Body>
        </soap:Envelope>
        """.trimIndent()

        val requestBody = bodyProfile.toRequestBody("text/xml; charset=utf-8".toMediaType())
        val respone = snApiService.profile(requestBody )
        val xmlProfile = respone.string()

        Log.d("PROFILE", xmlProfile)

        //Extraer el JSON del XML
        val jsonProfile = xmlProfile
            .substringAfter("<getAlumnoAcademicoWithLineamientoResult>")
            .substringBefore("</getAlumnoAcademicoWithLineamientoResult>")
            .trim()

        Log.d("JSONProfile", jsonProfile)

        //Convertir JSON a objeto
        val profile = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<ProfileStudent>(jsonProfile)

        return profile
    }

    override suspend fun getCargaAcademica(): List<CargaAcademica> {
        val bodyCarga = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getCargaAcademicaByAlumno xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()

        val requestBody = bodyCarga.toRequestBody("text/xml; charset=utf-8".toMediaType())
        val response = snApiService.cargaAcademica(requestBody)
        val xmlCarga = response.string()

        //Consologeando el response
        Log.d("CARGA", xmlCarga)

        //Extraer el json del xml
        val jsonCarga = xmlCarga
            .substringAfter("<getCargaAcademicaByAlumnoResult>")
            .substringBefore("</getCargaAcademicaByAlumnoResult>")

        val cargaAcademica = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<List<CargaAcademica>>(jsonCarga)


        return cargaAcademica
    }

    override suspend fun getCargaCardex(lineamiento: Int): List<Cardex> {
        val bodyKardex = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAllKardexConPromedioByAlumno xmlns="http://tempuri.org/">
                  <aluLineamiento>${lineamiento}</aluLineamiento>
                </getAllKardexConPromedioByAlumno>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()

        val requestBody = bodyKardex.toRequestBody("text/xml; charset=utf-8".toMediaType())
        val response = snApiService.getkardex(requestBody)
        val xmlCardex = response.string()

        Log.d("KARDEX", xmlCardex)

        val jsonKardex = xmlCardex
            .substringAfter("<getAllKardexConPromedioByAlumnoResult>")
            .substringBefore("</getAllKardexConPromedioByAlumnoResult>")

        Log.d("JSON cardex", jsonKardex)

        val cardex = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<CardexResponse>(jsonKardex)

        return cardex.listCardex
    }

    override suspend fun getCaliPorUnidad(): List<CalifacionUnidad> {
        val bodyCaliUnidad = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getCalifUnidadesByAlumno xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()

        val requestBody = bodyCaliUnidad.toRequestBody("text/xml; charset=utf-8".toMediaType())
        val response = snApiService.getCaliPorUnidad(requestBody)
        val xmlCaliUnidad = response.string()

        Log.d("CALI UNIDAD", xmlCaliUnidad)

        val jsonCaliUnidad = xmlCaliUnidad
            .substringAfter("<getCalifUnidadesByAlumnoResult>")
            .substringBefore("</getCalifUnidadesByAlumnoResult>")

        //Obtener el json
        val caliUnidad = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<List<CalifacionUnidad>>(jsonCaliUnidad)

        return caliUnidad
    }

    override suspend fun getCaliFinal(modEducativo: Int): List<CalificacionFinal> {
        val bodyCaliFinal = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAllCalifFinalByAlumnos xmlns="http://tempuri.org/">
                  <bytModEducativo>${modEducativo}</bytModEducativo>
                </getAllCalifFinalByAlumnos>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()

        val requestBody = bodyCaliFinal.toRequestBody("text/xml; charset=utf-8".toMediaType())
        val response = snApiService.getCaliFinal(requestBody)
        val xmlCaliFinal = response.string()

        Log.d("CALI FINAL", xmlCaliFinal)

        val jsonCaliFinal = xmlCaliFinal
            .substringAfter("<getAllCalifFinalByAlumnosResult>")
            .substringBefore("</getAllCalifFinalByAlumnosResult>")

        val caliFinal = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<List<CalificacionFinal>>(jsonCaliFinal)

        return caliFinal
    }

    suspend fun callHTTPS(){
        // Datos para la petición
        val matricula = "s20120999"
        val contrasenia = "MIPASS"
        val tipoUsuario = "ALUMNO" // o "DOCENTE", según corresponda

        // URL del servicio web SOAP
        val urlString = "https://sicenet.surguanajuato.tecnm.mx/ws/wsalumnos.asmx"

        // Cuerpo del mensaje SOAP
        val soapEnvelope = """
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <accesoLogin xmlns="http://tempuri.org/">
              <strMatricula>$matricula</strMatricula>
              <strContrasenia>$contrasenia</strContrasenia>
              <tipoUsuario>$tipoUsuario</tipoUsuario>
            </accesoLogin>
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()


        try {
            // Establecer la conexión HTTPS
            val url = URL(urlString)
            val connection = url.openConnection() as HttpsURLConnection

            // Configurar la conexión
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Host", "sicenet.surguanajuato.tecnm.mx")
            connection.setRequestProperty("Content-Type", "text/xml; charset=\"UTF-8\"")
            //connection.setRequestProperty("Sec-Fetch-Mode", "cors")
            connection.setRequestProperty("Cookie", ".ASPXANONYMOUS=MaWJCZ-X2gEkAAAAODU2ZjkyM2EtNWE3ZC00NTdlLWFhYTAtYjk5ZTE5MDlkODIzeI1pCwvskL6aqtre4eT8Atfq2Po1;")
            connection.setRequestProperty("Content-Length", soapEnvelope.length.toString())
            connection.setRequestProperty("SOAPAction", "\"http://tempuri.org/accesoLogin\"")

            // Enviar el cuerpo del mensaje SOAP
            val outputStream: OutputStream = connection.outputStream
            outputStream.write(soapEnvelope.toByteArray(Charsets.UTF_8))
            outputStream.close()

            // Leer la respuesta del servicio
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val cookies = connection.getHeaderField("Set-Cookie")
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String?
                val response = StringBuilder()

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                // Aquí puedes procesar la respuesta del servicio
                println("Respuesta del servicio: $response")
                Log.d("SXML","Respuesta del servicio: $response")
            } else {
                // Manejar errores de conexión
                println("Error en la conexión: $responseCode")
            }

            // Cerrar la conexión
            connection.disconnect()
        } catch (e: IOException) {
            // Manejar excepciones de conexión
            e.printStackTrace()
        }
    }

}
