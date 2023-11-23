package cz.vvoleman.phr.common.domain.model.healthcare.service

import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithInfoDomainModel

data class MedicalServiceWithWorkersDomainModel(
    val medicalService: MedicalServiceDomainModel,
    val workers: List<MedicalWorkerWithInfoDomainModel>
)
