package cz.vvoleman.featureMeasurement.domain.model.field.unit

data class UnitDomainModel(
    val code: String,
    val name: String,
    val multiplier: Number,
    val addition: Number,
)
