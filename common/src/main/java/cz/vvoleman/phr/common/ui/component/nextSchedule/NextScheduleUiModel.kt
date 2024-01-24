package cz.vvoleman.phr.common.ui.component.nextSchedule

import java.time.LocalDateTime

data class NextScheduleUiModel(
    val dateTime: LocalDateTime,
    val items: List<NextScheduleItemUiModel>,
)
