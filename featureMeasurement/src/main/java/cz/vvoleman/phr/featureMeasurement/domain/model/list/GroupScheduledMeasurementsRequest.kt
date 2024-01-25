package cz.vvoleman.phr.featureMeasurement.domain.model.list

import cz.vvoleman.phr.common.domain.model.enum.SortDirection

data class GroupScheduledMeasurementsRequest (
    val scheduleItems: List<ScheduledMeasurementGroupDomainModel>,
    val sortDirection: SortDirection
)
