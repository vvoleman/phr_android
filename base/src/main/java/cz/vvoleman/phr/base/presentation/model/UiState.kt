package cz.vvoleman.phr.base.presentation.model

sealed class UiState<out T> {
    data class Success<T: Any>(val data: T): UiState<T>()
    data class Error(val error: Throwable): UiState<Nothing>()
    object Loading: UiState<Nothing>()
}
