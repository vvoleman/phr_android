package cz.vvoleman.phr.featureMedicine.ui.mapper.list

import cz.vvoleman.phr.common.ui.mapper.PatientUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.presentation.model.list.ScheduleItemWithDetailsPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.ScheduleItemWithDetailsUiModel

class ScheduleItemWithDetailsUiModelToPresentationMapper (
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
