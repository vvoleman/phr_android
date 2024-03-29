package cz.vvoleman.phr.featureMedicine.domain.model.schedule

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class ScheduleItemDomainModel(
    val id: String,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
    val endingAt: LocalDateTime? = null,
    val quantity: String,
    val description: String? = null
)
