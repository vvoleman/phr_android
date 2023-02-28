package cz.vvoleman.phr.data.core.medical_record

import android.os.Parcelable
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.data.core.BaseData
import cz.vvoleman.phr.data.core.MedicalWorker
import cz.vvoleman.phr.data.core.Patient
import cz.vvoleman.phr.data.core.diagnose.Diagnose
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MedicalRecord(
    val id: Int? = null,
    val patient: Patient,
    val problemCategory: ProblemCategory? = null,
    val diagnose: Diagnose? = null,
    val medicalWorker: MedicalWorker? = null,
    val createdAt: LocalDate = LocalDate.now(),
    val assets: List<MedicalRecordAsset> = listOf(),
    val comment: String = ""
) : Parcelable, BaseData() {
    override fun getAdapterPair(): AdapterPair {
        return AdapterPair(id.toString(), "${problemCategory?.name} ${diagnose?.name}", this)
    }
}
