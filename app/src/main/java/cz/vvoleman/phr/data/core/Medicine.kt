package cz.vvoleman.phr.data.core

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Medicine(
    val id: String,
    val name: String,
    val dosage: String,
    val info: String,
    val expiresAt: LocalDate?,
    val createdAt: LocalDate?,
    val substances: List<Substance> = listOf(),
) : Parcelable, BaseData() {
    override fun getAdapterPair(): AdapterPair {
        return AdapterPair(id, name, this)
    }
}