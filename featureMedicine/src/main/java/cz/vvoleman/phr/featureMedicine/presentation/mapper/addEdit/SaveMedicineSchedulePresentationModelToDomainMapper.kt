package cz.vvoleman.phr.featureMedicine.presentation.mapper.addEdit

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveMedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.SaveMedicineSchedulePresentationModel

class SaveMedicineSchedulePresentationModelToDomainMapper(
    private val patientMapper: PatientPresentationModelToDomainMapper,
) {

    fun toDomain(model: SaveMedicineSchedulePresentationModel): SaveMedicineScheduleDomainModel {
        return SaveMedicineScheduleDomainModel(
            id = model.id,
            patient = patientMapper.toDomain(model.patient),
            medicine = model.medicine,
            schedules = model.schedules,
            createdAt = model.createdAt
        )
    }

    fun toPresentation(model: SaveMedicineScheduleDomainModel): SaveMedicineSchedulePresentationModel {
        return SaveMedicineSchedulePresentationModel(
            id = model.id,
            patient = patientMapper.toPresentation(model.patient),
            medicine = model.medicine,
            schedules = model.schedules,
            createdAt = model.createdAt
        )
    }

}