package data.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.HttpCookies
import session.SessionCookieStorage

/**
 *
 * ¿Qué hace el archivo?
 * Cada petición POST /ws/wsalumnos.asmx
 * Ktor hace:
 * 1. Buscar la cookie
 * 2. Agregar la cookie
 * 3. Enviar la peticion
 * 4. Guardar nuevas cookies
 *
 */

//Fabrica para crear el cliente HTTPCliente
object HttpClientFactory {

    fun create(): HttpClient {
        return HttpClient {
            install(HttpCookies) {
                storage =
                    SessionCookieStorage()
            }

        }

    }

}