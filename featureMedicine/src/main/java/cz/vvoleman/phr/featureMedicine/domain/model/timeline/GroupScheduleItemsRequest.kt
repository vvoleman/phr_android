package cz.vvoleman.phr.featureMedicine.domain.model.timeline

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import java.time.LocalDateTime

data class GroupScheduleItemsRequest(
    val scheduleItems: List<ScheduleItemWithDetailsDomainModel>,
    val currentDateTime: LocalDateTime
)
