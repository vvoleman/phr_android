package cz.vvoleman.featureMeasurement.data.model.field.unit

data class UnitGroupDataModel(
    val id: String,
    val name: String,
    val units: List<UnitDataModel>,
    val defaultUnit: UnitDataModel
)
