package cz.vvoleman.phr.featureMedicine.ui.model.list.schedule

import java.time.LocalDateTime

data class NextScheduleItemUiModel (
    val scheduleItems: List<ScheduleItemWithDetailsUiModel>,
    val dateTime: LocalDateTime
)