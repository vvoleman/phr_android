package cz.vvoleman.featureMeasurement.ui.mapper.core

import cz.vvoleman.featureMeasurement.presentation.model.core.field.unit.UnitGroupPresentationModel
import cz.vvoleman.featureMeasurement.ui.model.core.field.unit.UnitGroupUiModel

class UnitGroupUiModelToPresentationMapper(
    private val unitMapper: UnitUiModelToPresentationMapper
) {

    fun toPresentation(model: UnitGroupUiModel): UnitGroupPresentationModel {
        return UnitGroupPresentationModel(
            id = model.id,
            name = model.name,
            units = model.units.map { unitMapper.toPresentation(it) },
            defaultUnit = unitMapper.toPresentation(model.defaultUnit),
        )
    }

    fun toUi(model: UnitGroupPresentationModel): UnitGroupUiModel {
        return UnitGroupUiModel(
            id = model.id,
            name = model.name,
            units = model.units.map { unitMapper.toUi(it) },
            defaultUnit = unitMapper.toUi(model.defaultUnit),
        )
    }

}
