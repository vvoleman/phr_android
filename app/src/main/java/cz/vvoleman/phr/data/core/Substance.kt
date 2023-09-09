package cz.vvoleman.phr.data.core

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import kotlinx.parcelize.Parcelize

@Parcelize
data class Substance(
    val id: String,
    val name: String,
    val isAddictive: Boolean,
    val isDoping: Boolean
) : Parcelable, BaseData() {
    override fun getAdapterPair(): AdapterPair {
        return AdapterPair(id, name, this)
    }
}
