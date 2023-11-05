package cz.vvoleman.phr.featureMedicine.ui.model.list.schedule

import cz.vvoleman.phr.common.ui.model.PatientUiModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.MedicineUiModel

data class ScheduleItemWithDetailsUiModel(
    val scheduleItem: ScheduleItemUiModel,
    val medicine: MedicineUiModel,
    val patient: PatientUiModel,
    val medicineScheduleId: String,
    val isAlarmEnabled: Boolean
)
