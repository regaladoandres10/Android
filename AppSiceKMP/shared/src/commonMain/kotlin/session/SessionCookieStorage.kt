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

    private val storage = mutableListOf<Cookie>()

    init {
        // Restaurar las cookies desde la memoria al iniciar
        SessionManager.getCookies().forEach { cookieString ->
            val parts = cookieString.split("=", limit = 2)
            if (parts.size == 2) {
                storage.add(Cookie(name = parts[0], value = parts[1]))
            }
        }
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        storage.removeAll { it.name == cookie.name }
        storage.add(cookie)

        SessionManager.saveCookies(
            storage.map { "${it.name}=${it.value}" }
        )
    }

    override suspend fun get(requestUrl: Url): List<Cookie> {
        return storage
    }

    override fun close() {
        storage.clear()
    }
}