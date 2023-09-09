package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import java.time.LocalDate

data class AddEditPresentationModel(
    val recordId: String? = null,
    val createdAt: LocalDate = LocalDate.now(),
    val diagnoseId: String? = null,
    val problemCategoryId: String? = null,
    val visitDate: LocalDate,
    val patientId: String,
    val medicalWorkerId: String? = null,
    val assets: List<AssetPresentationModel> = listOf()
)
