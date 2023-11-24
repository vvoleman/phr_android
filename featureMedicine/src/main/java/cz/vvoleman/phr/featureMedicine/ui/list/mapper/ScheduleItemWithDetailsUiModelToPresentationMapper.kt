package cz.vvoleman.phr.featureMedicine.ui.list.mapper

import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ScheduleItemWithDetailsPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.ScheduleItemWithDetailsUiModel

class ScheduleItemWithDetailsUiModelToPresentationMapper(
    private val scheduleItemMapper: ScheduleItemUiModelToPresentationMapper,
    private val patientMapper: PatientUiModelToPresentationMapper,
    private val medicineMapper: MedicineUiModelToPresentationMapper,
) {

    fun toPresentation(model: ScheduleItemWithDetailsUiModel): ScheduleItemWithDetailsPresentationModel {
        return ScheduleItemWithDetailsPresentationModel(
            scheduleItem = scheduleItemMapper.toPresentation(model.scheduleItem),
            medicine = medicineMapper.toPresentation(model.medicine),
            patient = patientMapper.toPresentation(model.patient),
            medicineScheduleId = model.medicineScheduleId,
            isAlarmEnabled = model.isAlarmEnabled
        )
    }

    fun toUi(model: ScheduleItemWithDetailsPresentationModel): ScheduleItemWithDetailsUiModel {
        return ScheduleItemWithDetailsUiModel(
            scheduleItem = scheduleItemMapper.toUi(model.scheduleItem),
            medicine = medicineMapper.toUi(model.medicine),
            patient = patientMapper.toUi(model.patient),
            medicineScheduleId = model.medicineScheduleId,
            isAlarmEnabled = model.isAlarmEnabled
        )
    }
}
