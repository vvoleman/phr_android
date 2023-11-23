package cz.vvoleman.phr.common.domain.model.healthcare.worker

import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceWithInfoDomainModel

data class MedicalWorkerWithServicesDomainModel(
    val medicalWorker: MedicalWorkerDomainModel,
    val services: List<MedicalServiceWithInfoDomainModel>
)
