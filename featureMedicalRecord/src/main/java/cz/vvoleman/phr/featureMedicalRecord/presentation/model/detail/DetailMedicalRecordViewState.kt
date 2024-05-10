package cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordPresentationModel

data class DetailMedicalRecordViewState(
    val medicalRecord: MedicalRecordPresentationModel,
    val medicalFacility: MedicalFacilityPresentationModel? = null,
)
