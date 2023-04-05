package cz.vvoleman.phr.feature_medicine.domain.model.schedule

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class ScheduleItemDomainModel(
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
    val endingAt: LocalDateTime,
    val quantity: String,
    val description: String? = null,
)
