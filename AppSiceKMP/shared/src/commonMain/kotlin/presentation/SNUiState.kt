package presentation

sealed interface SNUiState {

    object Loading : SNUiState
    object Success : SNUiState
    data class Error(val message: String) : SNUiState
}