package cz.vvoleman.phr.featureMeasurement.ui.model.detail

import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupEntryUiModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupFieldUi

data class EntryInfoUiModel(
    val entry: MeasurementGroupEntryUiModel,
    val fields: List<MeasurementGroupFieldUi>,
)
