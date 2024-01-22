package cz.vvoleman.phr.featureMeasurement.presentation.model.core.field.unit

data class UnitGroupPresentationModel(
    val id: String,
    val name: String,
    val units: List<UnitPresentationModel>,
    val defaultUnit: UnitPresentationModel
)
