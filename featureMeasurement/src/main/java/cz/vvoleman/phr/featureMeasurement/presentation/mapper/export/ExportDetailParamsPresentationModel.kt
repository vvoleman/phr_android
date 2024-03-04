package cz.vvoleman.phr.featureMeasurement.presentation.mapper.export

import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel

data class ExportDetailParamsPresentationModel(
    val measurementGroup: MeasurementGroupPresentationModel,
    val entries: List<EntryInfoUiModel>
)
