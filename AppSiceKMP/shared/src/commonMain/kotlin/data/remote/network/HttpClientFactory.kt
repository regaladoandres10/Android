package data.remote.network

//Cliente principal de Ktor
import io.ktor.client.HttpClient

//Plugin para manejo automatico de cookies
import io.ktor.client.plugins.cookies.HttpCookies

//Interfaz para guardar las cookies
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