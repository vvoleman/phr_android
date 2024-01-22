package cz.vvoleman.phr.featureMeasurement.domain.model.core.field.unit

data class UnitDomainModel(
    val code: String,
    val name: String,
    val multiplier: Number,
    val addition: Number,
)
