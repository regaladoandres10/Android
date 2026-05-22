package session

/**
 * Este archivo remplaza SharedPreferences
 *
 * Este archivo se va a usar para guardar la sesión del usuario y mantener sesión en memoria
 *
 * La sesión se va a guardar en memoria compartida KMP
 */

object SessionManager {

    //Almacenamos todas las cookies en una lista
    // Guardamos cookies como estas: "ASP.NET_SessionId=123"
    private val cookies =
        mutableListOf<String>()

    fun saveCookies(
        newCookies: List<String>
    ) {
        //Limpiamos cookies anteriores
        cookies.clear()

        //Guardamos nuevas cookies
        cookies.addAll(
            newCookies
        )
    }


    fun getCookies(): List<String> {
        //Devuelve copia de las cookies actuales
        return cookies
    }


    fun clear() {
        //Borra todas las cookies
        cookies.clear()
    }

}