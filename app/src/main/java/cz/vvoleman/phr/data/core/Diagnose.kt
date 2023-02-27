package cz.vvoleman.phr.data.core

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import kotlinx.parcelize.Parcelize

@Parcelize
data class Diagnose(
    val id: String,
    val name: String,
) : Parcelable, BaseData() {
    override fun getAdapterPair(): AdapterPair {
        return AdapterPair(id, name, this)
    }
}
