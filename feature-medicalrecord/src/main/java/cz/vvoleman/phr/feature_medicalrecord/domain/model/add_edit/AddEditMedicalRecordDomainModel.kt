package cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit

import java.time.LocalDate

data class AddEditMedicalRecordDomainModel(
    val id: String? = null,
    val createdAt: LocalDate,
    val patientId: String,
    val medicalWorkerId: String? = null,
    val diagnoseId: String? = null,
    val recordIds: List<String> = listOf()
)