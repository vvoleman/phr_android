package cz.vvoleman.phr.featureMedicine.ui.model.list.schedule

import cz.vvoleman.phr.common.ui.model.PatientUiModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.MedicineUiModel
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class MedicineScheduleUiModel(
    val id: String,
    val patient: PatientUiModel,
    val medicine: MedicineUiModel,
    val schedules: List<ScheduleItemUiModel>,
    val createdAt: LocalDateTime,
    val isAlarmEnabled: Boolean,
) {

    fun getTimes(): List<LocalTime> {
        return schedules.map { it.time }.toSet().sorted()
    }

    fun getDays(): List<DayOfWeek> {
        return schedules.map { it.dayOfWeek }.toSet().sorted()
    }

}