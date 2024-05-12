package cz.vvoleman.phr.base.ui.mapper

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewStateBinder<VIEW_STATE : Any, VIEW_BINDING : Any, NOTIFICATION : Any> :
    ViewStateBinder<VIEW_STATE, VIEW_BINDING> {

    private val _notification = MutableSharedFlow<NOTIFICATION>()
    val notification = _notification.asSharedFlow()

    protected lateinit var lifecycleScope: CoroutineScope
    protected lateinit var fragmentContext: Context
    private var hasBeenBound = false

    override fun init(viewBinding: VIEW_BINDING, context: Context, lifecycleScope: LifecycleCoroutineScope) {
        this.lifecycleScope = lifecycleScope
        fragmentContext = context
        hasBeenBound = false
    }

    protected fun notify(notification: NOTIFICATION) {
        lifecycleScope.launch {
            _notification.emit(notification)
        }
    }

    override fun bind(viewBinding: VIEW_BINDING, viewState: VIEW_STATE) {
        if (!hasBeenBound) {
            firstBind(viewBinding, viewState)
            hasBeenBound = true
        }
    }

    open fun firstBind(viewBinding: VIEW_BINDING, viewState: VIEW_STATE) {}

    protected fun getVisibility(
        value: Boolean,
        visible: Int = android.view.View.VISIBLE,
        hidden: Int = android.view.View.GONE
    ): Int {
        return if (value) {
            visible
        } else {
            hidden
        }
    }

    override fun onDestroy(viewBinding: VIEW_BINDING) {
        lifecycleScope.cancel()
    }
}
