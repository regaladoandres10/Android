package di

import data.remote.api.SICENETService
import data.local.repository.*
import data.remote.datasource.RemoteDataSource
import data.remote.network.HttpClientFactory
import data.repository.NetworkSNRepository
import data.repository.SNRepository
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient

class DefaultAppContainer: AppContainer {

    //URL principal del servidor SICENET
    private val baseUrl = "https://sicenet.surguanajuato.tecnm.mx/"

    // Crear cliente HTTP.
    private val httpClient = HttpClientFactory.create()

    //Instancia de Ktorfit
    private val ktorfit =
        Ktorfit.Builder()
            .baseUrl(baseUrl) //URL del servicio
            //Configuramos el cliente HTTP
            .httpClient(httpClient)
            //Construir instancia
            .build()

    //Creamos el servicio de SICENETService
    private val service = ktorfit.create<SICENETService>()

    //Crear datasource.
    private val remoteDataSource = RemoteDataSource(service)

    //Repository principal.
    override val snRepository: SNRepository by lazy {
        NetworkSNRepository(remoteDataSource)
    }

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