package cz.vvoleman.phr.featureMedicine.presentation.list.model

import java.time.LocalDateTime

data class NextScheduleItemPresentationModel(
    val scheduleItems: List<ScheduleItemWithDetailsPresentationModel>,
    val dateTime: LocalDateTime
)
