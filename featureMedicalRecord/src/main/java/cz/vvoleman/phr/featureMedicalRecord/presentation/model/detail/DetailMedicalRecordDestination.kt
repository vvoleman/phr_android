package cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class DetailMedicalRecordDestination : PresentationDestination {
    data class OpenGallery(
        val medicalRecordId: String,
        val assetId: String?
    ) : DetailMedicalRecordDestination()
}
