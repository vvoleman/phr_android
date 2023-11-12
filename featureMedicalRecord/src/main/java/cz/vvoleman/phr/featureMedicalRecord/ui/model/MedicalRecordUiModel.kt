package cz.vvoleman.phr.featureMedicalRecord.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MedicalRecordUiModel(
    val id: String,
    val patient: String,
    val createdAt: LocalDate,
    val visitDate: LocalDate,
    val problemCategoryName: String? = null,
    val problemCategoryColor: String? = null,
    val diagnoseId: String? = null,
    val diagnoseName: String? = null,
    val medicalWorker: String? = null
) : Parcelable
