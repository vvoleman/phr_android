package cz.vvoleman.phr.featureMedicine.ui.list.mapper

import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.MedicineScheduleUiModel

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

    fun toUi(model: MedicineSchedulePresentationModel): MedicineScheduleUiModel {
        return MedicineScheduleUiModel(
            id = model.id,
            patient = patientMapper.toUi(model.patient),
            medicine = medicineMapper.toUi(model.medicine),
            schedules = model.schedules.map { scheduleMapper.toUi(it) },
            createdAt = model.createdAt,
            isAlarmEnabled = model.isAlarmEnabled
        )
    }
}
