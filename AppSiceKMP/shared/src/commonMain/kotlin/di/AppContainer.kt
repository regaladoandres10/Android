package data.local.database.di

import data.local.database.data.local.database.AppDatabase
import data.local.database.data.remote.api.SiceApi
import data.local.database.data.remote.network.HttpClientFactory
import data.local.database.data.repository.NetworkSNRepository
import data.local.repository.CalificacionFinalRepository
import data.local.repository.CalificacionUnidadRepository
import data.local.repository.CardexRepository
import data.local.repository.CargaAcademicaRepository
import data.local.repository.OfflineCalificacionFinalRepository
import data.local.repository.OfflineCalificacionURepository
import data.local.repository.OfflineCardexRepository
import data.local.repository.OfflineCargaAcademicaRepository
import data.local.repository.OfflineUsuarioRepository
import data.local.repository.UsuarioRepository

interface AppContainer {
    val snRepository: NetworkSNRepository
    val usuarioRepository: UsuarioRepository
    val cargaRepository: CargaAcademicaRepository
    val cardexRepository: CardexRepository
    val calificacionUnidadRepository: CalificacionUnidadRepository
    val calificacionFinalRepository: CalificacionFinalRepository
}

class DefaultAppContainer(private val database: AppDatabase): AppContainer {

    //Creando la instancia de httpClient
    private val httpClient = HttpClientFactory.create()

    private val ktorfit = HttpClientFactory
        .createKtorfit(httpClient)

    //Creando la instancia de la Api
    private val siceApi = ktorfit.create<SiceApi>()

    //Crear el repositorio

    override val snRepository: NetworkSNRepository by lazy {
        NetworkSNRepository(
            siceApi,
            usuarioRepository,
            cargaRepository,
            cardexRepository,
            calificacionUnidadRepository,
            calificacionFinalRepository
        )
    }

    private val usuarioDao =
        database.usuarioDao()

    private val cargaDao =
        database.cargaDao()

    private val cardexDao =
        database.cardexDao()

    private val calificacionUnidadDao =
        database.calificacionUnidadDao()

    private val calificacionFinalDao =
        database.calificacionFinalDao()


    override val usuarioRepository =
        OfflineUsuarioRepository(usuarioDao)

    override val cargaRepository =
        OfflineCargaAcademicaRepository(cargaDao)

    override val cardexRepository =
        OfflineCardexRepository(cardexDao)

    override val calificacionUnidadRepository: CalificacionUnidadRepository =
        OfflineCalificacionURepository(calificacionUnidadDao)
    override val calificacionFinalRepository: CalificacionFinalRepository =
        OfflineCalificacionFinalRepository(calificacionFinalDao)

}