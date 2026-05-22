package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.remote.model.ProfileStudent

import data.repository.SNRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi


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
    private val _uiState = MutableStateFlow<SNUiState>(SNUiState.Loading)

    //Estado expuesto para que Compose lo observe.
    val uiState: StateFlow<SNUiState> = _uiState.asStateFlow()

    /*
     Guarda el perfil obtenido.
     Empieza en null porque aún no se obtiene.
    */
    private val _profile = MutableStateFlow<ProfileStudent?>(null)

    /*
     Exponer perfil solo lectura.
    */
    val profile = _profile.asStateFlow()

    fun accesoSN(matricula: String, password: String) {

        viewModelScope.launch {
            try {
                _uiState.value = SNUiState.Loading
                /*
                 Ejecuta login.

                 La sesión se guarda automáticamente mediante SessionCookieStorage.
                */
                repository.acceso(matricula, password)

                /*
                 Obtener perfil usando la sesión.
                */
                val student = repository.profile()

                //Guardar perfil.
                _profile.value = student

                //Actualizar estado y UI
                _uiState.value = SNUiState.Success

            } catch (e: Exception) {
                _uiState.value =
                    SNUiState.Error(
                        e.message ?: "Error desconocido"
                    )
            }
        }
    }

    //Recargar perfil sin iniciar sesión.
    fun loadProfile() {
        viewModelScope.launch {
            try {
                _profile.value = repository.profile()
            } catch (_: Exception) {

            }
        }
    }

    //Obtener carga académica.
    suspend fun cargaAcademica() = repository.getCargaAcademica()

    //Obtener cardex.
    suspend fun cardex(lineamiento: Int) =
        repository.getCargaCardex(
            lineamiento
        )

    //Obtener calificaciones por unidad.
    suspend fun calificacionUnidad() = repository.getCaliPorUnidad()

    //Obtener calificación final.
    suspend fun calificacionFinal(mod: Int) = repository.getCaliFinal(mod)
}