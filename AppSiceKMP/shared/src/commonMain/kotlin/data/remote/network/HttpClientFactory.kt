package data.local.database.data.remote.network

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(): HttpClient {

        return HttpClient {

            followRedirects = true

            install(Logging) {
                level = LogLevel.ALL
            }

            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            defaultRequest {
                contentType(ContentType.Text.Xml)
                headers.append(
                    "Accept",
                    "text/xml"
                )
            }
        }
    }

    fun createKtorfit( client: HttpClient): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl("https://sicenet.surguanajuato.tecnm.mx/")
            .httpClient(client)
            .build()
    }
}