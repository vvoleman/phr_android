package cz.vvoleman.phr.featureMedicine.domain.model.timeline

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel

data class ToggleScheduleAlarmRequest(
    val schedule: ScheduleItemWithDetailsDomainModel,
    val newState: Boolean
)
