package session

/**
 * Este archivo remplaza ReceivedCookiesInterceptor y AddCookiesInterceptor
 *
 */

//Interfaz de ktor para guardar las cookies
import io.ktor.client.plugins.cookies.CookiesStorage

//Representa las cookies individuales
import io.ktor.http.Cookie

//URL donde se obtuvo o enviara la cookie
import io.ktor.http.Url

/**
 * Esta clase sirve para guardar las cookies y enviar cookies
 */

class SessionCookieStorage : CookiesStorage {

    //Lista interna de ktor para guardar las cookies
    private val storage = mutableListOf<Cookie>()

    //Se ejecuta cuando el servidor respondo con Set-Cookie
    override suspend fun addCookie(
        requestUrl: Url,
        cookie: Cookie
    ) {

        //Elimina todas las cookies con el mismo nombre
        storage.removeAll {
            it.name ==
                    cookie.name
        }

        //Agrega la nueva cookie
        storage.add(cookie)

        //Guarda las cookies en el archivo de sesión
        SessionManager.saveCookies(
            storage.map {
                "${it.name}=${it.value}"
            }
        )
    }

    //Se ejecuta cuando el cliente hace una petición
    override suspend fun get(
        requestUrl: Url
    ): List<Cookie> { return storage }

    //Limpiar la memoria
    override fun close() {
        storage.clear()
    }

}