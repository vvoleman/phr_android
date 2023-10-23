package cz.vvoleman.phr.featureMedicine.data.alarm

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek

@Parcelize
data class MedicineAlarmContent(
    val medicineScheduleId: String,
    val triggerAt: Long,
    val alarmDays: List<DayOfWeek>,
) : Parcelable
