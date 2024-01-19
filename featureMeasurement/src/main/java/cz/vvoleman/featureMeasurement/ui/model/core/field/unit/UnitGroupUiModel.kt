package cz.vvoleman.featureMeasurement.ui.model.core.field.unit

data class UnitGroupUiModel(
    val id: String,
    val name: String,
    val units: List<UnitUiModel>,
    val defaultUnit: UnitUiModel
)
