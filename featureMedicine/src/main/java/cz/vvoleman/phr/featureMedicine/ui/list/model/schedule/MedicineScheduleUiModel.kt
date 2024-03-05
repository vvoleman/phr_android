package cz.vvoleman.phr.featureMedicine.ui.list.model.schedule

import android.os.Parcelable
import cz.vvoleman.phr.common.ui.model.patient.PatientUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.MedicineUiModel
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Parcelize
data class MedicineScheduleUiModel(
    val id: String,
    val patient: PatientUiModel,
    val medicine: MedicineUiModel,
    val schedules: List<ScheduleItemUiModel>,
    val createdAt: LocalDateTime,
    val isAlarmEnabled: Boolean,
) : Parcelable {

    val isFinished: Boolean
        get() = schedules.all { it.endingAt?.isBefore(LocalDateTime.now()) ?: false }

    fun getTimes(): List<LocalTime> {
        return schedules.map { it.time }.toSet().sorted()
    }

    fun getDays(): List<DayOfWeek> {
        return schedules.map { it.dayOfWeek }.toSet().sorted()
    }
}
