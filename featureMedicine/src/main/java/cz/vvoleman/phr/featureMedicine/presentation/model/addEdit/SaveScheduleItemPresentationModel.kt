package cz.vvoleman.phr.featureMedicine.presentation.model.addEdit

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class SaveScheduleItemPresentationModel(
    val id: String? = null,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val scheduledAt: LocalDateTime = LocalDateTime.now(),
    val endingAt: LocalDateTime? = null,
    val quantity: Number,
    val unit: String,
    val description: String? = null
)
