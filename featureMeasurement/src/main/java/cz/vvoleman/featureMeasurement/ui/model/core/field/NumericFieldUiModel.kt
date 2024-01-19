package cz.vvoleman.featureMeasurement.ui.model.core.field

import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField
import cz.vvoleman.featureMeasurement.ui.model.core.field.unit.UnitGroupUiModel

data class NumericFieldUiModel(
    override val id: String,
    override val name: String,
    val unitGroup: UnitGroupUiModel,
) : MeasurementGroupField
