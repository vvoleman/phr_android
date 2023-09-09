package cz.vvoleman.phr.common.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class GroupedItemsDomainModel<ITEM : Parcelable>(
    val value: @RawValue Any,
    val items: List<ITEM>
) : Parcelable
