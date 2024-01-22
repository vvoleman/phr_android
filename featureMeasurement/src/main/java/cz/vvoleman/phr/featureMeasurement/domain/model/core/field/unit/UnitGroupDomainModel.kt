package cz.vvoleman.phr.featureMeasurement.domain.model.core.field.unit

data class UnitGroupDomainModel(
    val id: String,
    val name: String,
    val units: List<UnitDomainModel>,
    val defaultUnit: UnitDomainModel
)
