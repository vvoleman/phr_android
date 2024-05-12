package cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class AddEditPresentationModel(
    val recordId: String? = null,
    val createdAt: LocalDate = LocalDate.now(),
    val diagnose: DiagnosePresentationModel? = null,
    val problemCategoryId: String? = null,
    val visitDate: LocalDate,
    val patientId: String,
    val specificMedicalWorker: String? = null,
    val assets: List<AssetPresentationModel> = listOf()
) : Parcelable
