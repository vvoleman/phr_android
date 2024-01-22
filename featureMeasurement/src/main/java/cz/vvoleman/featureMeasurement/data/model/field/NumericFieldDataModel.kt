package cz.vvoleman.featureMeasurement.data.model.field

import cz.vvoleman.featureMeasurement.data.model.MeasurementGroupFieldData
import cz.vvoleman.featureMeasurement.data.model.field.unit.UnitGroupDataModel

data class NumericFieldDataModel(
    override val id: String,
    override val name: String,
    val unitGroup: UnitGroupDataModel,
    val minimalValue: Number?,
    val maximalValue: Number?,
) : MeasurementGroupFieldData
