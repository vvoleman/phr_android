package cz.vvoleman.phr.base.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.domain.exception.DomainException
import cz.vvoleman.phr.base.domain.usecase.UseCase
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<VIEW_STATE : Any, NOTIFICATION : Any>(
    @Suppress("UnusedPrivateProperty")
    protected val savedStateHandle: SavedStateHandle,
    protected val useCaseExecutorProvider: UseCaseExecutorProvider
) : ViewModel() {

    @Suppress("VariableNaming")
    protected abstract val TAG: String

    private val _viewState = MutableStateFlow<VIEW_STATE?>(null)
    val viewState = _viewState.asStateFlow()

    private val _notification = MutableSharedFlow<NOTIFICATION>()
    val notification = _notification.asSharedFlow()

    private val _destination = MutableSharedFlow<PresentationDestination>()
    val destination = _destination.asSharedFlow()

    protected val currentViewState: VIEW_STATE
        get() = viewState.value!!

    private val useCaseExecutor by lazy {
        useCaseExecutorProvider(viewModelScope)
    }

    open suspend fun onInit() {
        _viewState.value = setupState()
    }

    private suspend fun setupState(): VIEW_STATE {
        return initState()
    }

    protected abstract suspend fun initState(): VIEW_STATE

    protected fun <INPUT, OUTPUT> execute(
        useCase: UseCase<INPUT, OUTPUT>,
        value: INPUT,
        onSuccess: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) {
        useCaseExecutor.execute(useCase, value, onSuccess, onException)
    }

    protected open fun updateViewState(newViewState: VIEW_STATE) {
        _viewState.value = newViewState
    }

    protected fun notify(notification: NOTIFICATION) {
        viewModelScope.launch {
            Log.d(TAG, "notify: $notification")
            _notification.emit(notification)
        }
    }

    protected fun navigateTo(destination: PresentationDestination) {
        viewModelScope.launch {
            Log.d(TAG, "navigateTo: $destination")
            _destination.emit(destination)
        }
    }
}
