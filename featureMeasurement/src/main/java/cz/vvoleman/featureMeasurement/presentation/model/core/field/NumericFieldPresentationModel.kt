package cz.vvoleman.featureMeasurement.presentation.model.core.field

import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupFieldPresentation
import cz.vvoleman.featureMeasurement.presentation.model.core.field.unit.UnitGroupPresentationModel

data class NumericFieldPresentationModel(
    override val id: String,
    override val name: String,
    val unitGroup: UnitGroupPresentationModel?,
    val minimalValue: Number?,
    val maximalValue: Number?,
) : MeasurementGroupFieldPresentation
