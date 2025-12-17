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

class MediaEntryViewModel(private val mediaRepository: MultimediaRepository) : ViewModel() {

    var mediaUiState by mutableStateOf( MediaUiState() )
        private set

    //Update [NoteUiState] with the values
    fun updateUiState(mediaDetails: MediaDetails) {
        mediaUiState = MediaUiState(mediaDetails = mediaDetails)
    }

    //Save a media
    fun saveMedia() {
        if (!mediaUiState.mediaDetails.isValid()) return

        viewModelScope.launch {
            mediaRepository.insertMedia(mediaUiState.mediaDetails.toItem())
        }
    }

}

data class MediaUiState(
    val mediaDetails: MediaDetails = MediaDetails(),
    val isValid: Boolean = false
)

data class MediaDetails(
    val id: Int = 0,
    val ownerId: Int = -1,
    val ownerType: OwnerType = OwnerType.TASK,
    //Ruta del archivo
    val uri: String = "",
    //Tipo de archivo almacenado
    val type: FileType = FileType.NONE,
    val createdAt: Long? = null
) {
    fun isValid(): Boolean =
        ownerId > 0 && uri.isNotBlank()
}

fun MediaDetails.toItem(): Multimedia = Multimedia(
    id = id,
    ownerId = ownerId,
    ownerType = ownerType,
    uri = uri,
    type = type
)

fun Multimedia.toUiState() : MediaUiState = MediaUiState(
    mediaDetails = this.toDetails(),
    isValid = true
)

fun Multimedia.toDetails(): MediaDetails = MediaDetails(
    id = id,
    ownerId = ownerId,
    ownerType = ownerType,
    uri = uri,
    type = type
)