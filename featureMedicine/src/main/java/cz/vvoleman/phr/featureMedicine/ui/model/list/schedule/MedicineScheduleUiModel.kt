package cz.vvoleman.phr.featureMedicine.ui.model.list.schedule

import cz.vvoleman.phr.common.ui.model.PatientUiModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.MedicineUiModel
import java.time.LocalDateTime

data class MedicineScheduleUiModel(
    val id: String,
    val patient: PatientUiModel,
    val medicine: MedicineUiModel,
    val schedules: List<ScheduleItemUiModel>,
    val createdAt: LocalDateTime,
    val isAlarmEnabled: Boolean,
)