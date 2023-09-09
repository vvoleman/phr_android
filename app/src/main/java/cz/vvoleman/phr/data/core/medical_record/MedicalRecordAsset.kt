package cz.vvoleman.phr.data.core.medical_record

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.data.core.BaseData
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MedicalRecordAsset(
    val id: Int? = null,
    val type: AssetType,
    val uri: String,
    val createdAt: LocalDate
) : Parcelable, BaseData() {
    override fun getAdapterPair(): AdapterPair {
        return AdapterPair(id.toString(), type.name, this)
    }
}
