package cz.vvoleman.phr.common.domain

data class GroupedItemsDomainModel<ITEM: Any>(
    val value: Any,
    val items: List<ITEM>
)