package data.local.database.data.remote.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import okhttp3.RequestBody
import okhttp3.ResponseBody

interface SiceApi {
    /*
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/accesoLogin"
    )
     */
    @POST("ws/wsalumnos.asmx")
    suspend fun accesoLogin( @Body body: String ): String

    /*
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getAlumnoAcademicoWithLineamiento"
    )
     */
    @POST("ws/wsalumnos.asmx")
    suspend fun profile( @Body body: String ): String

    @POST("ws/wsalumnos.asmx")
    suspend fun cargaAcademica( @Body body: String ): String

    @POST("ws/wsalumnos.asmx")
    suspend fun cardex( @Body body: String ): String

    @POST("/ws/wsalumnos.asmx")
    suspend fun getCaliPorUnidad(@Body soap: String): String

    @POST("/ws/wsalumnos.asmx")
    suspend fun getCaliFinal(@Body soap: String): String

}