package cz.vvoleman.phr.featureMeasurement.domain.model.list

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import java.time.LocalDateTime

data class ScheduledMeasurementGroupDomainModel(
    val measurementGroup: MeasurementGroupDomainModel,
    val dateTime: LocalDateTime
)
