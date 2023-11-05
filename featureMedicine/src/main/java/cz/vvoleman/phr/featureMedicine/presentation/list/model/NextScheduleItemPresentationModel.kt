package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.featureMedicine.presentation.model.list.ScheduleItemWithDetailsPresentationModel
import java.time.LocalDateTime

data class NextScheduleItemPresentationModel(
    val scheduleItems: List<ScheduleItemWithDetailsPresentationModel>,
    val dateTime: LocalDateTime
)
