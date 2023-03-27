package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

import java.time.LocalDate

data class AddMedicalRecordAssetDomainModel(
    val medicalRecordId: String,
    val url: String,
    val createdAt: LocalDate = LocalDate.now()
)