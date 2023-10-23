package cz.vvoleman.phr.featureMedicine.ui.mapper.list

import cz.vvoleman.phr.common.ui.mapper.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.MedicineScheduleUiModel

class MedicineScheduleUiModelToPresentationMapper(
    private val patientMapper: PatientUiModelToPresentationMapper,
    private val scheduleMapper: ScheduleItemUiModelToPresentationMapper,
    private val medicineMapper: MedicineUiModelToPresentationMapper
) {

    fun toPresentation(model: MedicineScheduleUiModel): MedicineSchedulePresentationModel {
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