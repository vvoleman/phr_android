package cz.vvoleman.phr.featureMeasurement.data.model.core.field

import cz.vvoleman.phr.featureMeasurement.data.model.core.MeasurementGroupFieldData
import cz.vvoleman.phr.featureMeasurement.data.model.core.field.unit.UnitGroupDataModel

data class NumericFieldDataModel(
    override val id: String,
    override val name: String,
    val unitGroup: UnitGroupDataModel,
    val minimalValue: Number?,
    val maximalValue: Number?,
) : MeasurementGroupFieldData
