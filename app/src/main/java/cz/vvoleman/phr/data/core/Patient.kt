package cz.vvoleman.phr.data.core

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
data class Patient(
    val id: Int? = null,
    val name: String,
    val address: Address?,
    val birthDate: LocalDate?,
    val gender: Gender?
) : BaseData(), Parcelable {

    override fun getAdapterPair(): AdapterPair {
        return AdapterPair(id.toString(), name, this)
    }

    override fun toString(): String {
        return name
    }
}
