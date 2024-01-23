package cz.vvoleman.phr.common.ui.component.nextSchedule

import java.time.LocalDateTime

data class NextScheduleItemUiModel(
    val id: String,
    val time: LocalDateTime,
    val name: String,
    val additionalInfo: String? = null,
)
