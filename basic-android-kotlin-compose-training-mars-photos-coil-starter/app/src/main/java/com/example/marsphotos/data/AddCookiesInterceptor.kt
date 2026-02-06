import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences may ary, but this will work 99% of the time.
 */
class AddCookiesInterceptor(
    private val context: Context
) : Interceptor {

    companion object {
        const val PREF_COOKIES = "PREF_COOKIES"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val cookies = prefs.getStringSet(PREF_COOKIES, emptySet()) ?: emptySet()

        if (cookies.isNotEmpty()) {
            val cookieHeader = cookies.joinToString("; ") {
                it.split(";")[0] // solo nombre=valor
            }
            builder.addHeader("Cookie", cookieHeader)
        }

        return chain.proceed(builder.build())
    }
}
