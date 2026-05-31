package ui.viewmodel

sealed interface SNUiState {
    data object Idle : SNUiState
    object Loading : SNUiState
    object Success : SNUiState
    data class Error(val message: String) : SNUiState
}