package data.local.database.di

import data.local.database.data.remote.api.SiceApi
import data.local.database.data.remote.network.HttpClientFactory
import data.local.database.data.repository.NetworkSNRepository

interface AppContainer {
    val snRepository: NetworkSNRepository
}

class DefaultAppContainer: AppContainer {

    //Creando la instancia de httpClient
    private val httpClient = HttpClientFactory.create()

    private val ktorfit = HttpClientFactory
        .createKtorfit(httpClient)

    //Creando la instancia de la Api
    private val siceApi = ktorfit.create<SiceApi>()

    override val snRepository: NetworkSNRepository by lazy {
        NetworkSNRepository(siceApi)
    }
}