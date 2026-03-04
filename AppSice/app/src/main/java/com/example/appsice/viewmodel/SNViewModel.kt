/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:OptIn(InternalSerializationApi::class)

package com.example.appsice.viewmodel

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.appsice.SNApplication
import com.example.appsice.data.SNWMRepository
import com.example.appsice.data.local.repository.CalificacionFinalRepository
import com.example.appsice.data.local.repository.CalificacionUnidadRepository
import com.example.appsice.data.local.repository.CardexRepository
import com.example.appsice.data.local.repository.CargaAcademicaRepository
import com.example.appsice.data.local.repository.UsuarioRepository
import com.example.appsice.data.repository.SNRepository
import com.example.appsice.data.remote.model.MarsPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface SNUiState {
    object Success: SNUiState
    object Error : SNUiState
    object Loading : SNUiState
}

/*
    Probar si estamos obteniendo las cookies

    class SNViewModel(
    application: Application,
    private val snRepository: SNRepository
) : AndroidViewModel(application) {
}

 */

@OptIn(InternalSerializationApi::class)
class SNViewModel(
    application: Application,
    private val snRepository: SNRepository,
    private val syncRepository: SNWMRepository,
    private val usuarioRepository: UsuarioRepository,
    private val cargaRepository: CargaAcademicaRepository,
    private val cardexRepository: CardexRepository,
    private val caliUnidadRepository: CalificacionUnidadRepository,
    private val caliFinalRepository: CalificacionFinalRepository
) : AndroidViewModel(application) {
    /** The mutable State that stores the status of the most recent request */
    var snUiState: SNUiState by mutableStateOf(SNUiState.Loading)
        private set

    val usuarioFlow = usuarioRepository.getAllUsuarioStream()
    val cargaFlow = cargaRepository.getAllCargaStream()
    val cardexFlow = cardexRepository.getAllCardexStream()
    val caliUnidadFlow = caliUnidadRepository.getAllCalificacionUStream()
    val caliFinalFlow = caliFinalRepository.getAllCalisFinaltream()
    val syncState = syncRepository.logintWorkInfo
    val cargaState = syncRepository.cargaWorkInfo
    val cardexState = syncRepository.cardexWorkInfo
    val caliUnidadState = syncRepository.caliUnidadWorkInfo
    val caliFinal = syncRepository.caliFinalWorkInfo

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        //accesoSN("", "")
        //loadProfile()
    }

    /**
     * Obtenemos el perfil del alumno
     */

    /*
    fun loadProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val profile = snRepository.profile()

                Log.d("VIEWMODEL_PROFILE", profile.toString())

                SNUiState.Success(profile)
            } catch (e: IOException) {
                SNUiState.Error
            }
        }
    }
     */

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun accesoSN(matricula: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            snUiState = SNUiState.Loading
            try {
                //val result = snRepository.acceso(matricula, password)
                //Login
                snRepository.acceso(matricula, password)

                //Si las credenciales son correctas cambiar el estado
                snUiState = SNUiState.Success

                //Verificar cookies
                val prefs = PreferenceManager
                    .getDefaultSharedPreferences(getApplication())

                //Verificación de cookies
                val cookies = prefs.getStringSet("PREF_COOKIES", emptySet())
                Log.d("COOKIES", cookies.toString())

                //Llamar el repository para sincronizar datos
                syncRepository.profile()


            } catch (e: IOException) {
                snUiState = SNUiState.Error
            } catch (e: HttpException) {
                snUiState = SNUiState.Error
            }
        }
    }

    fun cargaAcademica() {
        syncRepository.cargaAcademica()
    }
    fun cardex() {
        syncRepository.cardex()
    }
    fun calificacionUnidad() {
        syncRepository.calificacionUnidad()
    }

    fun calificacionFinal() {
        syncRepository.calificacionFinal()
    }

    /*
    * val prefs = PreferenceManager.getDefaultSharedPreferences(appContext)
                val cookies = prefs.getStringSet("PREF_COOKIES", emptySet())
                Log.d("COOKIES", cookies.toString())
    * */

    /**
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                //Obtenemos la instancia global de la app
                val application = (this[APPLICATION_KEY] as SNApplication)
                val snRepository = application.container.snRepository
                val syncRepository = application.container.syncRepository
                val usuarioRepository = application.container.usuarioRepository
                val cargaRepository = application.container.cargaAcademicaRepository
                val cardexRepository = application.container.cardexRepository
                val caliUnidadRepository = application.container.calificacionUnidadRepository
                val caliFinalRepository = application.container.calificacionFinalRepository
                SNViewModel(
                    application = application,
                    snRepository = snRepository,
                    syncRepository = syncRepository,
                    usuarioRepository = usuarioRepository,
                    cargaRepository = cargaRepository,
                    cardexRepository = cardexRepository,
                    caliUnidadRepository = caliUnidadRepository,
                    caliFinalRepository = caliFinalRepository
                )
            }
        }
    }
}
