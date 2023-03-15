package cz.vvoleman.phr.common.ui.adapter

import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding

interface OnAdapterItemListener<T: Any> {

    fun onItemClicked(item: T)

    fun onItemOptionsMenuClicked(item: T)

    fun bind(binding: ItemGroupedItemsBinding, item: GroupedItemsUiModel<T>)

}