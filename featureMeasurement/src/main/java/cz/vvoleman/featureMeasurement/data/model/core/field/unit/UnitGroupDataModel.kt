package cz.vvoleman.featureMeasurement.data.model.core.field.unit

data class UnitGroupDataModel(
    val id: String,
    val name: String,
    val units: List<UnitDataModel>,
    val defaultUnit: UnitDataModel
)
