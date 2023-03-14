package cz.vvoleman.phr.common.ui.model

data class GroupedItemsUiModel<VALUE,ITEM: Any>(
    val value: VALUE,
    val items: List<ITEM>
)