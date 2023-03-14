package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import java.time.LocalDate

data class AddEditPresentationModel(
    val createdAt: LocalDate,
    val diagnoseId: String,
    val problemCategoryId: String,
    val patientId: String,
    val medicalWorkerId: String,
    val recordIds: List<String>
)