package cz.vvoleman.featureMeasurement.ui.mapper.core

import cz.vvoleman.featureMeasurement.presentation.model.core.field.NumericFieldPresentationModel
import cz.vvoleman.featureMeasurement.ui.model.core.field.NumericFieldUiModel

class NumericFieldUiModelToPresentationMapper(
    private val unitGroupMapper: UnitGroupUiModelToPresentationMapper,
) {

    fun toPresentation(model: NumericFieldUiModel): NumericFieldPresentationModel {
        return NumericFieldPresentationModel(
            id = model.id,
            name = model.name,
            unitGroup = unitGroupMapper.toPresentation(model.unitGroup),
        )
    }

    fun toUi(model: NumericFieldPresentationModel): NumericFieldUiModel {
        return NumericFieldUiModel(
            id = model.id,
            name = model.name,
            unitGroup = unitGroupMapper.toUi(model.unitGroup),
        )
    }

}
