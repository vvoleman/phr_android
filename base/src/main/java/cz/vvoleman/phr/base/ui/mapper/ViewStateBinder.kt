package cz.vvoleman.phr.base.ui.mapper

import android.content.Context
import kotlinx.coroutines.CoroutineScope

interface ViewStateBinder<VIEW_STATE : Any, VIEW_BINDING : Any> {

    fun bind(viewBinding: VIEW_BINDING, viewState: VIEW_STATE)

    fun init(viewBinding: VIEW_BINDING, context: Context, lifecycleScope: CoroutineScope)
}
