package cz.vvoleman.phr.featureMeasurement.ui.mapper.core

import cz.vvoleman.phr.featureMeasurement.presentation.model.core.field.unit.UnitPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.field.unit.UnitUiModel

class UnitUiModelToPresentationMapper {

    fun toPresentation(model: UnitUiModel): UnitPresentationModel {
        return UnitPresentationModel(
            code = model.code,
            name = model.name,
            multiplier = model.multiplier,
            addition = model.addition,
        )
    }

    fun toUi(model: UnitPresentationModel): UnitUiModel {
        return UnitUiModel(
            code = model.code,
            name = model.name,
            multiplier = model.multiplier,
            addition = model.addition,
        )
    }
}
