package data.remote.util

/**
 * Este archivo nos permite [construir el XML] para las llamadas HTTP
 */

object SoapBuilder {
    /*
    Recibe únicamente el contenido
    interno del Body.
    */
    private fun envelope( body: String ): String {
        return """
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
            <soap:Body>
                $body
            </soap:Body>
        </soap:Envelope>

        """.trimIndent()
    }

    //Login
    fun acceso(
        matricula: String,
        password: String
    ): String {
        return envelope(
            """
            <accesoLogin
                xmlns="http://tempuri.org/">
                <strMatricula>
                    $matricula
                </strMatricula>
                <strContrasenia>
                    $password
                </strContrasenia>
                <tipoUsuario>
                    ALUMNO
                </tipoUsuario>
            </accesoLogin>
            """
        )

    }

    //Perfil
    fun profile(): String {
        return envelope(
            """
            <getAlumnoAcademicoWithLineamiento
                xmlns="http://tempuri.org/" />
            """
        )
    }

    //Carga académica
    fun getCargaAcademica(): String {
        return envelope(
            """
            <getCargaAcademicaByAlumno
                xmlns="http://tempuri.org/" />
            """
        )
    }

    // Cardex
    fun getCargaCardex( lineamiento: Int ): String {
        return envelope(
            """
            <getAllKardexConPromedioByAlumno
                xmlns="http://tempuri.org/">

                <aluLineamiento>
                    $lineamiento
                </aluLineamiento>

            </getAllKardexConPromedioByAlumno>
            """
        )
    }

    //Calificación por unidad
    fun getCaliPorUnidad(): String {
        return envelope(
            """
            <getCalifUnidadesByAlumno
                xmlns="http://tempuri.org/" />
            """
        )
    }

    // Calificación final
    fun getCaliFinal(modEducativo: Int): String {
        return envelope(
            """
            <getAllCalifFinalByAlumnos
                xmlns="http://tempuri.org/">

                <bytModEducativo>
                    $modEducativo
                </bytModEducativo>

            </getAllCalifFinalByAlumnos>
            """
        )
    }
}