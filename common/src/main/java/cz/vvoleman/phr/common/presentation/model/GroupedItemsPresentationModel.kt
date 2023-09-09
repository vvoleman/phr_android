package cz.vvoleman.phr.common.presentation.model

data class GroupedItemsPresentationModel<ITEM : Any>(
    val value: Any,
    val items: List<ITEM>
)
