package cz.vvoleman.featureMeasurement.data.model.field.unit

data class UnitDataModel(
    val code: String,
    val name: String,
    val multiplier: Number,
    val addition: Number,
)
