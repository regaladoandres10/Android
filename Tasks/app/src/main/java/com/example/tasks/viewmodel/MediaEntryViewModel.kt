package com.example.tasks.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.MultimediaRepository
import com.example.tasks.data.local.entities.Multimedia
import com.example.tasks.static.FileType
import com.example.tasks.static.OwnerType
import kotlinx.coroutines.launch

//ViewModel for Create and Insert media
class MediaEntryViewModel(
    private val mediaRepository: MultimediaRepository
) : ViewModel() {

    var mediaUiState by mutableStateOf( MediaUiState() )
        private set

    //Update [NoteUiState] with the values
    fun updateUiState(mediaDetails: MediaDetails) {
        mediaUiState = mediaUiState.copy(
            mediaDetails = mediaDetails
        )
    }

    //Save a media
    fun saveMedia() {
        val details = mediaUiState.mediaDetails
        if (!details.isValid()) return

        viewModelScope.launch {
            mediaRepository.insertMedia(details.toItem())
        }
    }

}

data class MediaUiState(
    val mediaDetails: MediaDetails = MediaDetails()
) {
    val isValid: Boolean
        get() = mediaDetails.isValid()
}

data class MediaDetails(
    val id: Int = 0,
    val ownerId: Int? = null,
    val ownerType: OwnerType = OwnerType.TASK,
    //Ruta del archivo
    val uri: String = "",
    //Tipo de archivo almacenado
    val type: FileType = FileType.NONE,
    val createdAt: Long? = null
) {
    fun isValid(): Boolean =
        ownerId != null && ownerId > 0 && uri.isNotBlank()
}

fun MediaDetails.toItem(): Multimedia {
    requireNotNull(ownerId) { "ownerId no puede ser null al guardar Multimedia" }

    return Multimedia(
        id = id,
        ownerId = ownerId,
        ownerType = ownerType,
        uri = uri,
        type = type,
    )
}

fun Multimedia.toUiState(): MediaUiState =
    MediaUiState(mediaDetails = toDetails())

fun Multimedia.toDetails(): MediaDetails =
    MediaDetails(
        id = id,
        ownerId = ownerId,
        ownerType = ownerType,
        uri = uri,
        type = type,
        createdAt = createdAt
    )