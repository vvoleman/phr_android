package cz.vvoleman.phr.feature_medicalrecord.ui.model

import java.time.LocalDate

data class MedicalRecordUiModel(
    val id: String,
    val patient: String,
    val createdAt: LocalDate,
    val visitDate: LocalDate,
    val problemCategoryName: String? = null,
    val problemCategoryColor: String? = null,
    val diagnoseId: String? = null,
    val diagnoseName: String? = null,
    val medicalWorker: String? = null,
)
