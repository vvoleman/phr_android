package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile

import java.time.LocalDate

data class AddMedicalRecordAssetDomainModel(
    val medicalRecordId: String,
    val url: String,
    val createdAt: LocalDate = LocalDate.now()
)
