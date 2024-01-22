package cz.vvoleman.phr.featureMeasurement.data.alarm.receiver.measurementGroup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek

@Parcelize
data class MeasurementGroupAlarmContent(
    val measurementGroupId: String,
    val triggerAt: Long,
    val alarmDays: List<DayOfWeek>,
) : Parcelable
