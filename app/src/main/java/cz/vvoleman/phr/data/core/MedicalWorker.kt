package cz.vvoleman.phr.data.core

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalWorker(
    val id: Int? = null,
    val name: String,
    val email: String?,
    val phone: String?,
    val address: Address?
) : Parcelable, BaseData() {

    override fun getAdapterPair(): AdapterPair {
        return AdapterPair("${id}name", name, this)
    }
}
