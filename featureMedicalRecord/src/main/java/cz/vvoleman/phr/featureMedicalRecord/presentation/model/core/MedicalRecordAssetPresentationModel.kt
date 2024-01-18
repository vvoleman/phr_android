package cz.vvoleman.phr.featureMedicalRecord.presentation.model.core

import java.time.LocalDate

data class MedicalRecordAssetPresentationModel(
    val id: String,
    val url: String,
    val medicalRecordId: String,
    val createdAt: LocalDate = LocalDate.now()
)
