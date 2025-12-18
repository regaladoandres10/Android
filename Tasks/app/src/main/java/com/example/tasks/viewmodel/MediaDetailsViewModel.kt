package com.example.tasks.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.MultimediaRepository
import com.example.tasks.models.Owner
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
    private val _owner = MutableStateFlow<Owner?>(null)

    //Media temporal cuando se crea una tarea nueva
    private val _localMedia = MutableStateFlow<List<MediaDetails>>(emptyList())

    //Media desde base de datos modo edicion
    private val remoteMedia: Flow<List<MediaDetails>> =
        _owner
            .filterNotNull()
            .flatMapLatest { owner ->
                multimediaRepository.getMediaByOwner(
                    ownerId = owner.id,
                    ownerType = owner.type
                )
            }
            .map { list ->
                list.map { it.toDetails() }
            }

    val mediaForOwner: StateFlow<List<MediaDetails>> =
        combine(
            _owner,
            _localMedia,
            remoteMedia.onStart { emit(emptyList()) }
        ) { owner, local, remote ->
            when (owner) {
                null -> local                     // Creando
                else -> remote + local            // Editando
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )

    //Llamara solo cuando se edita una tarea existente
    fun load(ownerId: Int, ownerType: OwnerType) {
        _owner.value = Owner(ownerId, ownerType)
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
        when {
            media.ownerId == null -> {
                // Media local
                _localMedia.update { it - media }
            }

            else -> {
                // Media persistida
                viewModelScope.launch {
                    multimediaRepository.deleteMedia(media.toItem())
                }
            }
        }
    }

    /**
     * Guardar toda la media local cuando ya existe la tarea
     */
    fun saveAll(ownerId: Int, ownerType: OwnerType) {
        val mediaToSave = _localMedia.value
        if (mediaToSave.isEmpty()) return

        viewModelScope.launch {
            mediaToSave.forEach { media ->
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