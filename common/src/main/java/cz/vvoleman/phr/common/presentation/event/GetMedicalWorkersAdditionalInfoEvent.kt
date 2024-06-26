package cz.vvoleman.phr.common.presentation.event

import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel

data class GetMedicalWorkersAdditionalInfoEvent(
    val medicalWorker: List<MedicalWorkerDomainModel>,
)
