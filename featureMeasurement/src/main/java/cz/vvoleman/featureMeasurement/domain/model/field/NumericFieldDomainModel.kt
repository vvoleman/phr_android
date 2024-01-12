package cz.vvoleman.featureMeasurement.domain.model.field

import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField
import cz.vvoleman.featureMeasurement.domain.model.field.unit.UnitGroupDomainModel

data class NumericFieldDomainModel(
    override val id: String,
    override val name: String,
    val unitGroup: UnitGroupDomainModel,
) : MeasurementGroupField
