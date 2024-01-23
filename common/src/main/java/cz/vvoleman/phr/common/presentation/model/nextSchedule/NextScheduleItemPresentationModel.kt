package cz.vvoleman.phr.common.presentation.model.nextSchedule

import java.time.LocalDateTime

data class NextScheduleItemPresentationModel(
    val id: String,
    val time: LocalDateTime,
    val name: String,
    val additionalInfo: String? = null,
)
