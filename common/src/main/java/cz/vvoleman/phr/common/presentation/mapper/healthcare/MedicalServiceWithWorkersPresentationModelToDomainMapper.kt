package cz.vvoleman.phr.common.presentation.mapper.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceWithWorkersDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalServiceWithWorkersPresentationModel

class MedicalServiceWithWorkersPresentationModelToDomainMapper(
    private val serviceMapper: MedicalServicePresentationModelToDomainMapper,
    private val workerInfoMapper: MedicalWorkerWithInfoPresentationModelToDomainMapper,
) {

    fun toDomain(model: MedicalServiceWithWorkersPresentationModel): MedicalServiceWithWorkersDomainModel {
        return MedicalServiceWithWorkersDomainModel(
            medicalService = serviceMapper.toDomain(model.medicalService),
            workers = model.workers.map { workerInfoMapper.toDomain(it) }
        )
    }

    fun toPresentation(model: MedicalServiceWithWorkersDomainModel): MedicalServiceWithWorkersPresentationModel {
        return MedicalServiceWithWorkersPresentationModel(
            medicalService = serviceMapper.toPresentation(model.medicalService),
            workers = model.workers.map { workerInfoMapper.toPresentation(it) }
        )
    }
}
