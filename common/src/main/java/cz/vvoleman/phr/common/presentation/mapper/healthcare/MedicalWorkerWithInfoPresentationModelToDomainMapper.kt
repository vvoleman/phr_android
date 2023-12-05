package cz.vvoleman.phr.common.presentation.mapper.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithInfoDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerWithInfoPresentationModel

class MedicalWorkerWithInfoPresentationModelToDomainMapper(
    private val workerMapper: MedicalWorkerPresentationModelToDomainMapper,
) {

    fun toDomain(model: MedicalWorkerWithInfoPresentationModel): MedicalWorkerWithInfoDomainModel {
        return MedicalWorkerWithInfoDomainModel(
            medicalWorker = workerMapper.toDomain(model.medicalWorker),
            email = model.email,
            telephone = model.telephone,
        )
    }

    fun toPresentation(model: MedicalWorkerWithInfoDomainModel): MedicalWorkerWithInfoPresentationModel {
        return MedicalWorkerWithInfoPresentationModel(
            medicalWorker = workerMapper.toPresentation(model.medicalWorker),
            email = model.email,
            telephone = model.telephone,
        )
    }
}
