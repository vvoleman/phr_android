package cz.vvoleman.phr.featureMedicine.domain.model.schedule.save

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class SaveScheduleItemDomainModel(
    val id: String? = null,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime = LocalDateTime.now(),
    val endingAt: LocalDateTime? = null,
    val quantity: Number,
    val unit: String,
    val description: String? = null
)
