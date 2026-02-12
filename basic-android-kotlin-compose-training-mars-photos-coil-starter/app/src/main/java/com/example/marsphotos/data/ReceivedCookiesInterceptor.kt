import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Recibe las cookies del servidor y las guarda
 *
 * */
class ReceivedCookiesInterceptor(context: Context?) : Interceptor {
    private val context: Context?

    init {
        this.context = context
    } // AddCookiesInterceptor()

    @Throws(IOException::class)
    //Se ejecuta cada vez que una petición HTTP recibe una respuesta.
    // chain: Interceptor.Chain = Permite continuar la petición y obtener la respuesta
    public override fun intercept(chain: Interceptor.Chain): Response {
        /*
        * Se envía la peticion/solicitud (request) al servidor
        * Se recibe la respuesta HTTP
        * Se guarda en originalResponse
        *
        * */
        val originalResponse: Response = chain.proceed(chain.request())

        //Revisar si hay cookies en la respuesta
        //Busca headers llamados "Set-Cookie"
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            //Leer cookies guardadass previamente
            val cookies = PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet("PREF_COOKIES", HashSet<String?>()) as HashSet<String?>

            //Guardar las cookies nuevas
            //Ejemplo: ASP.NET_SessionId=abc123;
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }

            //Guardar en sharedPreferences (guarda las cookies internamente en la aplicación)
            val memes = PreferenceManager.getDefaultSharedPreferences(context).edit()
            memes.putStringSet("PREF_COOKIES", cookies).apply()
            memes.commit()
        }

        //Regresamos la respuesta
        return originalResponse
    }
}
