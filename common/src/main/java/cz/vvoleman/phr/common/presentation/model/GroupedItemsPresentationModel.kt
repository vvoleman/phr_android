package cz.vvoleman.phr.common.presentation.model

data class GroupedItemsPresentationModel<VALUE,ITEM: Any>(
    val value: VALUE,
    val items: List<ITEM>
)