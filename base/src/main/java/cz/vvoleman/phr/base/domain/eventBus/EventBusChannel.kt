package cz.vvoleman.phr.base.domain.eventBus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventBusChannel<T : Any, K : Any> {

    private val _eventFlow = MutableSharedFlow<T>()
    val events = _eventFlow.asSharedFlow()
    private val _listeners = mutableMapOf<String, suspend (T) -> K>()

    suspend fun pushEvent(event: T): List<K> {
        _eventFlow.emit(event)
        return _listeners.map { (_, listener) ->
            listener(event)
        }
    }

    fun addListener(id: String, listener: suspend (T) -> K) {
        _listeners[id] = listener
    }
}
