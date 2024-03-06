package cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.SpecificMedicalWorkerPresentationModel

data class DetailMedicalWorkerViewState(
    val medicalWorker: MedicalWorkerPresentationModel,
    val specificWorkers: List<SpecificMedicalWorkerPresentationModel>,
    val facilities: List<MedicalFacilityPresentationModel>
)
