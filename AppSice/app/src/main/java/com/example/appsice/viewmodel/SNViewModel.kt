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
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import com.example.appsice.MarsPhotosApplication
import com.example.appsice.data.repository.SNRepository
import com.example.appsice.data.remote.model.MarsPhoto
import com.example.appsice.data.remote.model.ProfileStudent
import com.example.appsice.workers.LoginWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface SNUiState {
    data class Success(val profile: ProfileStudent) : SNUiState
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
    private val snRepository: SNRepository
) : AndroidViewModel(application) {
    /** The mutable State that stores the status of the most recent request */
    var snUiState: SNUiState by mutableStateOf(SNUiState.Loading)
        private set

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

                //Verificar cookies
                val prefs = PreferenceManager
                    .getDefaultSharedPreferences(getApplication())

                //Verificación de cookies
                val cookies = prefs.getStringSet("PREF_COOKIES", emptySet())
                Log.d("COOKIES", cookies.toString())

                //Obtener el perfil
                val profile = snRepository.profile()

                //Pintando el nombre
                Log.d("Nombre", profile.nombre ?: "")


                val cargaAcademica = snRepository.getCargaAcademica()
                Log.d("Carga grupo", cargaAcademica.toString())

                val kardex = snRepository.getCargaCardex(3)
                //Log.d("")

                val caliUnidad = snRepository.getCaliPorUnidad()

                val caliFinal = snRepository.getCaliFinal(2)

                //Actualizar el estado final
                snUiState = SNUiState.Success(profile)
                //Cargar el perfil del alumno
                //loadProfile()

            } catch (e: IOException) {
                snUiState = SNUiState.Error
            } catch (e: HttpException) {
                snUiState = SNUiState.Error
            }
        }
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
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val snRepository = application.container.snRepository
                SNViewModel(
                    application = application,
                    snRepository = snRepository
                )
            }
        }
    }
}
