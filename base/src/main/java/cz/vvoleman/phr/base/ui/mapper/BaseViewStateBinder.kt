package cz.vvoleman.phr.base.ui.mapper

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewStateBinder<VIEW_STATE: Any, VIEW_BINDING: Any, NOTIFICATION: Any> : ViewStateBinder<VIEW_STATE, VIEW_BINDING> {

    private val _notification = MutableSharedFlow<NOTIFICATION>()
    val notification = _notification.asSharedFlow()

    protected lateinit var lifecycleScope: CoroutineScope
    protected lateinit var fragmentContext: Context

    override fun init(viewBinding: VIEW_BINDING, context: Context, lifecycleScope: CoroutineScope) {
        this.lifecycleScope = lifecycleScope
        fragmentContext = context
    }

    protected fun notify(notification: NOTIFICATION) {
        lifecycleScope.launch {
            _notification.emit(notification)
        }
    }

}
