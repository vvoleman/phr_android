package cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry

import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupEntryPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel

data class AddEditEntryViewState(
    val existingEntry: MeasurementGroupEntryPresentationModel? = null,
    val measurementGroup: MeasurementGroupPresentationModel,
    val entries: Map<String, MeasurementGroupEntryPresentationModel>
)
