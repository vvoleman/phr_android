package cz.vvoleman.phr.common.presentation.model.healthcare.list

import cz.vvoleman.phr.common.presentation.model.healthcare.core.AdditionalInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel

data class ListHealthcareViewState(
    val patient: PatientPresentationModel? = null,
    val medicalWorkers: Map<MedicalWorkerPresentationModel, List<AdditionalInfoPresentationModel<MedicalWorkerPresentationModel>>>? = null,
    val medicalFacilities: Map<MedicalFacilityPresentationModel, List<AdditionalInfoPresentationModel<MedicalFacilityPresentationModel>>>? = null,
)
