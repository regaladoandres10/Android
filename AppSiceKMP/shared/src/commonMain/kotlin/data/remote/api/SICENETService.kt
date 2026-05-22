package data.remote.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST

/**
 * Este archivo nos permite crear las llamadas HTTP o declarar las llamadas HTTP
 */

interface SICENETService {
    // Login
    @Headers(
        "Content-Type: text/xml;charset=utf-8",
        "SOAPAction: http://tempuri.org/accesoLogin"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun acceso(@Body soap: String): String


    // Perfil
    @Headers(
        "Content-Type: text/xml;charset=utf-8",
        "SOAPAction: http://tempuri.org/getAlumnoAcademicoWithLineamiento"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun profile(@Body soap: String): String


    // Carga académica
    @Headers(
        "Content-Type: text/xml;charset=utf-8",
        "SOAPAction: http://tempuri.org/getCargaAcademicaByAlumno"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun cargaAcademica(@Body soap: String): String


    // Cardex
    @Headers(
        "Content-Type: text/xml;charset=utf-8",
        "SOAPAction: http://tempuri.org/getAllKardexConPromedioByAlumno"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getKardex(@Body soap: String): String


    // Calificaciones unidad
    @Headers(
        "Content-Type: text/xml;charset=utf-8",
        "SOAPAction: http://tempuri.org/getCalifUnidadesByAlumno"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getCaliPorUnidad(@Body soap: String): String


    // Calificación final
    @Headers(
        "Content-Type: text/xml;charset=utf-8",
        "SOAPAction: http://tempuri.org/getAllCalifFinalByAlumnos"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getCaliFinal(@Body soap: String): String
}