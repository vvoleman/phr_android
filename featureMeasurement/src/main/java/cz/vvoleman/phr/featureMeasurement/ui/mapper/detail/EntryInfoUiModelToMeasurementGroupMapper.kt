package cz.vvoleman.phr.featureMeasurement.ui.mapper.detail

import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel

class EntryInfoUiModelToMeasurementGroupMapper(
    private val measurementGroupMapper: MeasurementGroupUiModelToPresentationMapper,
) {

    fun toUi(model: MeasurementGroupPresentationModel): List<EntryInfoUiModel> {
        val uiModel = measurementGroupMapper.toUi(model)

        return uiModel.entries.map { entry ->
            EntryInfoUiModel(
                entry = entry,
                fields = uiModel.fields,
            )
        }
    }

}
