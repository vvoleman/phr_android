package cz.vvoleman.phr.common.ui.adapter.grouped

import android.view.View

interface OnAdapterItemListener<T : Any> {

    fun onItemClicked(item: T)

    fun onItemDelete(item: T)

    fun onItemOptionsMenuClicked(item: T, anchorView: View)

}
