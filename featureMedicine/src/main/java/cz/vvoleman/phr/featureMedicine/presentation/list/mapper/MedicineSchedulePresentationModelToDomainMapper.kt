package cz.vvoleman.phr.featureMedicine.presentation.list.mapper

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicineSchedulePresentationModel

class MedicineSchedulePresentationModelToDomainMapper(
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val scheduleMapper: ScheduleItemPresentationModelToDomainMapper,
    private val medicineMapper: MedicinePresentationModelToDomainMapper
) {

    fun toDomain(model: MedicineSchedulePresentationModel): MedicineScheduleDomainModel {
        return MedicineScheduleDomainModel(
            id = model.id,
            patient = patientMapper.toDomain(model.patient),
            medicine = medicineMapper.toDomain(model.medicine),
            schedules = model.schedules.map { scheduleMapper.toDomain(it) },
            createdAt = model.createdAt,
            isAlarmEnabled = model.isAlarmEnabled
        )
    }

    fun toPresentation(model: MedicineScheduleDomainModel): MedicineSchedulePresentationModel {
        return MedicineSchedulePresentationModel(
            id = model.id,
            patient = patientMapper.toPresentation(model.patient),
            medicine = medicineMapper.toPresentation(model.medicine),
            schedules = model.schedules.map { scheduleMapper.toPresentation(it) },
            createdAt = model.createdAt,
            isAlarmEnabled = model.isAlarmEnabled
        )
    }
}
