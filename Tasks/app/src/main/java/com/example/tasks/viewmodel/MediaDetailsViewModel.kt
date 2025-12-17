package com.example.tasks.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.MultimediaRepository
import com.example.tasks.navigation.Destinations
import com.example.tasks.static.OwnerType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//ViewModel for read, list and delete media
class MediaDetailsViewModel(
    private val multimediaRepository: MultimediaRepository
) : ViewModel() {

    //Cuando se edita una tarea existente
    private val _owner = MutableStateFlow<Pair<Int, OwnerType>?>(null)

    //Media temporal cuando se crea una tarea nueva
    private val _localMedia = MutableStateFlow<List<MediaDetails>>(emptyList())

    //Media desde base de datos modo edicion
    private val remoteMedia: Flow<List<MediaDetails>> =
        _owner.filterNotNull()
            .flatMapLatest { (id, type) ->
                multimediaRepository.getMediaByOwner(id, type)
            }
            .map { list -> list.map { it.toDetails() } }

    val mediaForOwner: StateFlow<List<MediaDetails>> =
        combine(
            _owner,
            _localMedia,
            remoteMedia.onStart { emit(emptyList()) }
        ) { owner, local, remote ->
            if (owner == null) {
                // Creando tarea → media en memoria
                local
            } else {
                // Editando tarea → media en BD
                remote
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )

    //Llamara solo cuando se edita una tarea existente
    fun load(ownerId: Int, ownerType: OwnerType) {
        _owner.value = ownerId to ownerType
    }

    /**
     * Agregar media temporal (crear tarea)
     */
    fun addLocalMedia(media: MediaDetails) {
        _localMedia.update { it + media }
    }

    /**
     * Eliminar media (local o remota)
     */
    fun delete(media: MediaDetails) {
        val owner = _owner.value
        if (owner == null) {
            // Media local
            _localMedia.update { it - media }
        } else {
            // Media en BD
            viewModelScope.launch {
                multimediaRepository.deleteMedia(media.toItem())
            }
        }
    }

    /**
     * Guardar toda la media local cuando ya existe la tarea
     */
    fun saveAll(ownerId: Int, ownerType: OwnerType) {
        viewModelScope.launch {
            _localMedia.value.forEach { media ->
                multimediaRepository.insertMedia(
                    media.copy(
                        ownerId = ownerId,
                        ownerType = ownerType
                    ).toItem()
                )
            }
            _localMedia.value = emptyList()
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

/**
 * UI State for
 */
data class MediaDetailsUiState(
    val mediaList: List<MediaDetails> = emptyList()
)