package cz.vvoleman.phr.featureMedicine.presentation.addEdit.mapper

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveMedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicinePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.ScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.SaveMedicineSchedulePresentationModel

class SaveMedicineSchedulePresentationModelToDomainMapper(
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
    private val scheduleMapper: ScheduleItemPresentationModelToDomainMapper
) {

    fun toDomain(model: SaveMedicineSchedulePresentationModel): SaveMedicineScheduleDomainModel {
        return SaveMedicineScheduleDomainModel(
            id = model.id,
            patient = patientMapper.toDomain(model.patient),
            medicine = medicineMapper.toDomain(model.medicine),
            schedules = model.schedules.map { scheduleMapper.toDomain(it) },
            createdAt = model.createdAt
        )
    }

    fun toPresentation(model: SaveMedicineScheduleDomainModel): SaveMedicineSchedulePresentationModel {
        return SaveMedicineSchedulePresentationModel(
            id = model.id,
            patient = patientMapper.toPresentation(model.patient),
            medicine = medicineMapper.toPresentation(model.medicine),
            schedules = model.schedules.map { scheduleMapper.toPresentation(it) },
            createdAt = model.createdAt
        )
    }

}