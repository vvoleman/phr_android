package cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupEntryDomainModel
import java.time.LocalDateTime

data class SaveEntryRequest(
    val measurementGroup: MeasurementGroupDomainModel,
    val entry: MeasurementGroupEntryDomainModel?,
    val entryFields: List<EntryFieldDomainModel>,
    val dateTime: LocalDateTime,
)
