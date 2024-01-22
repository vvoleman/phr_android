package cz.vvoleman.phr.featureMeasurement.data.model.core.field.unit

data class UnitDataModel(
    val code: String,
    val name: String,
    val multiplier: Number,
    val addition: Number,
)
