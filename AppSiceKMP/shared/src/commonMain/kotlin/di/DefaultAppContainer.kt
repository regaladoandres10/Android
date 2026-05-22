package di

import data.remote.api.SICENETService
import data.local.repository.*
import data.remote.network.HttpClientFactory
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient

class DefaultAppContainer(
    //Recibe el cliente HTTP ya configurado (cookies, headers)
    httpClient: HttpClient
) : AppContainer {

    //URL principal del servidor SICENET
    private val baseUrl = "https://sicenet.surguanajuato.tecnm.mx/"

    //Instancia de Ktorfit
    private val ktorfit =
        Ktorfit.Builder()
            .baseUrl(baseUrl) //URL del servicio
            //Configuramos el cliente HTTP
            .httpClient(HttpClientFactory.create())
            //Construir instancia
            .build()

    //Creamos implementación automatica de SICENETService
    private val service = ktorfit.create<SICENETService>()

    //Crear repositorio remoto usando el servicio de HTTP
    /*
    override val syncRepository: SNWMRepository
        get() = TODO()
     */

    //Repositorios sincronizados
    override val usuarioRepository: UsuarioRepository
        get() = TODO()

    override val cargaAcademicaRepository: CargaAcademicaRepository
        get() = TODO()

    override val cardexRepository: CardexRepository
        get() = TODO()

    override val calificacionUnidadRepository:
            CalificacionUnidadRepository
        get() = TODO()

    override val calificacionFinalRepository:
            CalificacionFinalRepository
        get() = TODO()
}