package com.example.tasks.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.MultimediaRepository
import com.example.tasks.navigation.Destinations
import com.example.tasks.static.OwnerType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MediaDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val multimediaRepository: MultimediaRepository
) : ViewModel() {

    private val ownerId: Int = checkNotNull(savedStateHandle[Destinations.OWNER_ID])

    private val ownerType: OwnerType = checkNotNull(savedStateHandle[Destinations.OWNER_TYPE])

    /**
     * Holds the item details ui state. The data is retrieved from [MultimediaRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<MediaDetailsUiState> =
        multimediaRepository.getMediaByOwner(ownerId, ownerType)
            .map { mediaList ->
                MediaDetailsUiState(mediaList.map { it.toDetails() })
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MediaDetailsUiState()
            )

    fun deleteAllMedia() {
        viewModelScope.launch {
            multimediaRepository.deleteByOwner(ownerId, ownerType)
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