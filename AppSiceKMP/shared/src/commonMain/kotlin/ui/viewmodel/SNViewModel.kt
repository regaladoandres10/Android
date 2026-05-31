package ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.database.data.remote.model.LoginResponse
import data.local.database.data.repository.SNRepository
import data.remote.model.CalificacionFinal
import data.remote.model.CalificacionUnidad
import data.remote.model.Cardex
import data.remote.model.CargaAcademica
import data.remote.model.ProfileStudent

//import data.repository.SNRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json


@OptIn(InternalSerializationApi::class)
class SNViewModel(
    private val repository: SNRepository
) : ViewModel() {

    /*
     Estado actual de la pantalla.
     Loading -> está cargando
     Success -> operación correcta
     Error -> ocurrió un error
    */
    private val _uiState = MutableStateFlow<SNUiState>(SNUiState.Idle)

    //Estado expuesto para que Compose lo observe.
    val uiState: StateFlow<SNUiState> = _uiState.asStateFlow()

    /*
     Guarda el perfil obtenido.
     Empieza en null porque aún no se obtiene.
     */

    private val _profile = MutableStateFlow<ProfileStudent?>(null)
    //Exponer perfil solo lectura.
    val profile = _profile.asStateFlow()

    private val _loginResult =
        MutableStateFlow<String?>(null)

    val loginResult: StateFlow<String?> =
        _loginResult

    private val _cargaAcademica =
        MutableStateFlow<List<CargaAcademica>>(emptyList())

    val cargaAcademica =
        _cargaAcademica.asStateFlow()


    private val _cardex =
        MutableStateFlow<List<Cardex>>(emptyList())

    val cardex =
        _cardex.asStateFlow()

    private val _caliUnidad =
        MutableStateFlow<List<CalificacionUnidad>>(emptyList())

    val caliUnidad =
        _caliUnidad.asStateFlow()

    private val _caliFinal =
        MutableStateFlow<List<CalificacionFinal>>(emptyList())

    val caliFinal =
        _caliFinal.asStateFlow()

    private var currentMatricula: String? = null

    fun login(matricula: String, password: String) {

        viewModelScope.launch {
            try {
                //Login online
                _uiState.value = SNUiState.Loading
                repository.initSession()

                val response =
                    repository.acceso(
                        matricula,
                        password
                    )

                println("LOGIN RESPONSE:")
                println(response)

                println("ANTES DE DESERIALIZAR")

                val login =
                    Json {
                        ignoreUnknownKeys = true
                    }.decodeFromString<LoginResponse>(
                        response
                    )

                println("DESPUES DE DESERIALIZAR")
                println(login)

                println("ACCESO:")
                println(login.acceso)

                if (login.acceso == true) {

                    currentMatricula = matricula
                    println("LOGIN CORRECTO")

                    _uiState.value = SNUiState.Success

                    println("UI STATE SUCCESS")
                } else {
                    _uiState.value =
                        SNUiState.Error("Credenciales incorrectas")
                }

                /*
                 Obtener perfil usando la sesión.

                val student = repository.profile()

                //Guardar perfil.
                _profile.value = student

                //Actualizar estado y UI
                _uiState.value = SNUiState.Success
                 */

            } catch (e: Exception) {

                println("ERROR LOGIN:")
                println(e.message)

                //e.printStackTrace()

                val existeLocal = repository.loginOffline(matricula)

                if (existeLocal) {
                    println("LOGIN OFFLINE")
                    currentMatricula = matricula
                    _uiState.value = SNUiState.Success
                } else {
                    _uiState.value =
                        SNUiState.Error(
                            e.message ?: "No existe información local para esta matrícula"
                        )
                }

                /*
                _loginResult.value =
                    e.message ?: "Error"

                 */

                /*
                _uiState.value =
                    SNUiState.Error(
                        e.message ?: "Error desconocido"
                    )
                 */
            }
        }
    }


    //Recargar perfil sin iniciar sesión.
    fun loadProfile() {
        viewModelScope.launch {
            try {

                //Internet
                val student =
                repository.profile()

                _profile.value = student

                println("NOMBRE:")
                println(student.nombre)

                println("MATRICULA:")
                println(student.matricula)

            } catch (e: Exception) {
                val matricula = currentMatricula

                println("MATRICULA ACTUAL:")
                println(currentMatricula)
                if (matricula != null) {

                    val student =
                        repository.profileOffline(
                            matricula
                        )

                    if (student != null) {
                        _profile.value = student
                    }

                }
                println(e.message)
            }
        }
    }


    //Obtener carga académica.
    fun loadCargaAcademica() {
        viewModelScope.launch {
            try {
                val carga =
                    repository.getCargaAcademica()
                _cargaAcademica.value = carga
                println("MATERIAS:")
                println(carga.size)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    //Obtener cardex.
    fun loadCardex() {
        viewModelScope.launch {
            try {
                val cardex =
                    repository.getCardex(3)
                _cardex.value = cardex
                println("CARDEX:")
                println(cardex.size)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }


    //Obtener calificaciones por unidad.
    fun loadCaliUnidad() {
        viewModelScope.launch {
            try {
                val caliUnidad =
                    repository.getCaliPorUnidad()
                _caliUnidad.value = caliUnidad
                println("CaliUnidad:")
                println(caliUnidad.size)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    //Obtener calificación final.
    fun loadCaliFinal() {
        viewModelScope.launch {
            try {
                val caliFinal =
                    repository.getCaliFinal(2)
                _caliFinal.value = caliFinal
                println("CaliFinal:")
                println(caliFinal.size)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

}
