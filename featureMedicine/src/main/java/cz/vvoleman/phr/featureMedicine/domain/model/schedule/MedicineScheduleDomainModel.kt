package cz.vvoleman.phr.featureMedicine.domain.model.schedule

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Parcelize
data class MedicineScheduleDomainModel(
    val id: String,
    val patient: PatientDomainModel,
    val medicine: MedicineDomainModel,
    val schedules: List<ScheduleItemDomainModel>,
    val createdAt: LocalDateTime,
    val isAlarmEnabled: Boolean,
) : Parcelable
{
    fun getTimes(): List<LocalTime> {
        return schedules.map { it.time }.toSet().sorted()
    }

    fun getDays(): List<DayOfWeek> {
        return schedules.map { it.dayOfWeek }.toSet().sorted()
    }
}