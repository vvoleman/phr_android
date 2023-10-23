package cz.vvoleman.phr.featureMedicine.ui.model.list.schedule

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class ScheduleItemUiModel(
    val id: String? = null,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime,
    val endingAt: LocalDateTime? = null,
    val quantity: Number,
    val unit: String,
    val description: String? = null
)
