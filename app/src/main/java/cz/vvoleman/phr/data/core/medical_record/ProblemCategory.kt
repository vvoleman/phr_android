package cz.vvoleman.phr.data.core.medical_record

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.data.core.BaseData
import cz.vvoleman.phr.data.core.Color
import cz.vvoleman.phr.data.core.Patient
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class ProblemCategory(
    val id: Int,
    val name: String,
    val createdAt: LocalDate = LocalDate.now(),
    val color: Color,
    var patientId: Int,
) : Parcelable, BaseData() {

    override fun getAdapterPair(): AdapterPair {
        return AdapterPair(id.toString(), name, this)
    }
}
