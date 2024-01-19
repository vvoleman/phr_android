package cz.vvoleman.featureMeasurement.presentation.model.core.field

import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField
import cz.vvoleman.featureMeasurement.presentation.model.core.field.unit.UnitGroupPresentationModel

data class NumericFieldPresentationModel(
    override val id: String,
    override val name: String,
    val unitGroup: UnitGroupPresentationModel,
) : MeasurementGroupField
