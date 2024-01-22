package cz.vvoleman.phr.featureMeasurement.presentation.model.core.field.unit

data class UnitPresentationModel(
    val code: String,
    val name: String,
    val multiplier: Number,
    val addition: Number,
)
