package cz.vvoleman.phr.common.domain

data class GroupedItemsDomainModel<VALUE,ITEM: Any>(
    val value: VALUE,
    val items: List<ITEM>
)