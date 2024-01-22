package cz.vvoleman.phr.featureMeasurement.domain.model.core.field

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupFieldDomain
import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.unit.UnitGroupDomainModel

data class NumericFieldDomainModel(
    override val id: String,
    override val name: String,
    val unitGroup: UnitGroupDomainModel,
    val minimalValue: Number?,
    val maximalValue: Number?,
) : MeasurementGroupFieldDomain
