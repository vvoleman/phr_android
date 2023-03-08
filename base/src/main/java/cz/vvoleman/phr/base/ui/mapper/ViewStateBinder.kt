package cz.vvoleman.phr.base.ui.mapper

import androidx.viewbinding.ViewBinding

interface ViewStateBinder<VIEW_STATE: Any, VIEW_BINDING: ViewBinding> {

    fun bind(viewBinding: VIEW_BINDING, viewState: VIEW_STATE)

}