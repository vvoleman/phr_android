package cz.vvoleman.phr.featureMedicalRecord.presentation.model.detailGallery

import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordPresentationModel

data class DetailGalleryViewState(
    val medicalRecord: MedicalRecordPresentationModel,
    val selectedAssetId: String?
)
