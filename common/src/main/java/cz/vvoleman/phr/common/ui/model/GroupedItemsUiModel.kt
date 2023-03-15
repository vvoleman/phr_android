package cz.vvoleman.phr.common.ui.model

data class GroupedItemsUiModel<ITEM: Any>(
    val value: Any,
    val items: List<ITEM>
)