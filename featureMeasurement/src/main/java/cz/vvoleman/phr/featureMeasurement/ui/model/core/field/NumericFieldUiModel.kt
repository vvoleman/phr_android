package cz.vvoleman.phr.featureMeasurement.ui.model.core.field

import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupFieldUi
import cz.vvoleman.phr.featureMeasurement.ui.model.core.field.unit.UnitGroupUiModel

data class NumericFieldUiModel(
    override val id: String,
    override val name: String,
    val unitGroup: UnitGroupUiModel? = null,
    val minimalValue: Number? = null,
    val maximalValue: Number? = null,
) : MeasurementGroupFieldUi
