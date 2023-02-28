package cz.vvoleman.phr.data.core.diagnose

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.data.core.BaseData
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
