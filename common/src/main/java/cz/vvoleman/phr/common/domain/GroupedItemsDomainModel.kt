package cz.vvoleman.phr.common.domain

import kotlinx.parcelize.RawValue
import java.io.Serializable

data class GroupedItemsDomainModel<ITEM : Any>(
    val value: @RawValue Any,
    val items: List<ITEM>
) : Serializable
