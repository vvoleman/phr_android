package cz.vvoleman.phr.feature_medicine.data.model.schedule

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class ScheduleItemDataModel(
    val id: String? = null,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
    val endingAt: LocalDateTime? = null,
    val quantity: String,
    val description: String? = null
)
