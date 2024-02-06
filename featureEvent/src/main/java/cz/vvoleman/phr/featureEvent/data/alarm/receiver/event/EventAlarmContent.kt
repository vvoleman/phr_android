package cz.vvoleman.phr.featureEvent.data.alarm.receiver.event

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventAlarmContent(
    val eventId: String,
    val triggerAt: Long,
) : Parcelable
