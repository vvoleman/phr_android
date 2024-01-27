package cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry

data class EntryFieldPresentationModel(
    val fieldId: String,
    val name: String,
    val value: String? = null,
)
