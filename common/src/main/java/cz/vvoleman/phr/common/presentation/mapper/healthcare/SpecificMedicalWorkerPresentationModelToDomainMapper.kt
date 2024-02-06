package cz.vvoleman.phr.common.presentation.mapper.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.SpecificMedicalWorkerPresentationModel

class SpecificMedicalWorkerPresentationModelToDomainMapper(
    private val medicalWorkerMapper: MedicalWorkerPresentationModelToDomainMapper,
    private val medicalServiceMapper: MedicalServicePresentationModelToDomainMapper
) {

    fun toDomain(model: SpecificMedicalWorkerPresentationModel): SpecificMedicalWorkerDomainModel {
        return SpecificMedicalWorkerDomainModel(
            id = model.id,
            telephone = model.telephone,
            email = model.email,
            medicalWorker = medicalWorkerMapper.toDomain(model.medicalWorker!!),
            medicalService = medicalServiceMapper.toDomain(model.medicalService!!)
        )
    }

    fun toDomain(models: List<SpecificMedicalWorkerPresentationModel>): List<SpecificMedicalWorkerDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toPresentation(model: SpecificMedicalWorkerDomainModel): SpecificMedicalWorkerPresentationModel {
        return SpecificMedicalWorkerPresentationModel(
            id = model.id,
            telephone = model.telephone ?: "",
            email = model.email ?: "",
            medicalWorker = medicalWorkerMapper.toPresentation(model.medicalWorker),
            medicalService = medicalServiceMapper.toPresentation(model.medicalService)
        )
    }

    fun toPresentation(models: List<SpecificMedicalWorkerDomainModel>): List<SpecificMedicalWorkerPresentationModel> {
        return models.map { toPresentation(it) }
    }

}
