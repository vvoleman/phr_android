package cz.vvoleman.phr.common.presentation.mapper.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerPresentationModel

class MedicalWorkerPresentationModelToDomainMapper {

    fun toDomain(model: MedicalWorkerPresentationModel): MedicalWorkerDomainModel {
        return MedicalWorkerDomainModel(
            id = model.id,
            name = model.name,
            patientId = model.patientId
        )
    }

    fun toPresentation(model: MedicalWorkerDomainModel): MedicalWorkerPresentationModel {
        return MedicalWorkerPresentationModel(
            id = model.id,
            name = model.name,
            patientId = model.patientId
        )
    }
}
