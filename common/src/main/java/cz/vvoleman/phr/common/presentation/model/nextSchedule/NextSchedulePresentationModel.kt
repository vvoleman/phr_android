package cz.vvoleman.phr.common.presentation.model.nextSchedule

import java.time.LocalDateTime

data class NextSchedulePresentationModel(
    val dateTime: LocalDateTime,
    val items: List<NextScheduleItemPresentationModel>
)
