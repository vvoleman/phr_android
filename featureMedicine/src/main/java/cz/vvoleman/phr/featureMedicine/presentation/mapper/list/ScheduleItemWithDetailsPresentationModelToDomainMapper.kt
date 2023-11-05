package cz.vvoleman.phr.featureMedicine.presentation.mapper.list

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.ScheduleItemWithDetailsPresentationModel

class ScheduleItemWithDetailsPresentationModelToDomainMapper(
    private val scheduleItemMapper: ScheduleItemPresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val medicineMapper: MedicinePresentationModelToDomainMapper,
) {

    fun toDomain(model: ScheduleItemWithDetailsPresentationModel): ScheduleItemWithDetailsDomainModel {
        return ScheduleItemWithDetailsDomainModel(
            scheduleItem = scheduleItemMapper.toDomain(model.scheduleItem),
            medicine = medicineMapper.toDomain(model.medicine),
            patient = patientMapper.toDomain(model.patient),
            medicineScheduleId = model.medicineScheduleId,
            isAlarmEnabled = model.isAlarmEnabled
        )
    }

    fun toPresentation(model: ScheduleItemWithDetailsDomainModel): ScheduleItemWithDetailsPresentationModel {
        return ScheduleItemWithDetailsPresentationModel(
            scheduleItem = scheduleItemMapper.toPresentation(model.scheduleItem),
            medicine = medicineMapper.toPresentation(model.medicine),
            patient = patientMapper.toPresentation(model.patient),
            medicineScheduleId = model.medicineScheduleId,
            isAlarmEnabled = model.isAlarmEnabled
        )
    }

}