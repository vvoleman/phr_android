package cz.vvoleman.phr.common.presentation.model.grouped

data class GroupedItemsPresentationModel<ITEM : Any>(
    val value: Any,
    val items: List<ITEM>
)
