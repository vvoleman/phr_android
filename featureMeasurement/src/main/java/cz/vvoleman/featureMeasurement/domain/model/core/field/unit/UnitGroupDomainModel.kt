package cz.vvoleman.featureMeasurement.domain.model.core.field.unit

data class UnitGroupDomainModel(
    val id: String,
    val name: String,
    val units: List<UnitDomainModel>,
    val defaultUnit: UnitDomainModel
)
