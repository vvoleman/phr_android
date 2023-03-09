package cz.vvoleman.phr.base.ui.mapper

interface ViewStateBinder<VIEW_STATE: Any, VIEW_BINDING: Any> {

    fun bind(viewBinding: VIEW_BINDING, viewState: VIEW_STATE)

}