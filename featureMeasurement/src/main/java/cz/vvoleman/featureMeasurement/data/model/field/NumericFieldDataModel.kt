package cz.vvoleman.featureMeasurement.data.model.field

import cz.vvoleman.featureMeasurement.data.model.field.unit.UnitGroupDataModel
import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField

data class NumericFieldDataModel(
    override val id: String,
    override val name: String,
    val unitGroup: UnitGroupDataModel,
) : MeasurementGroupField
