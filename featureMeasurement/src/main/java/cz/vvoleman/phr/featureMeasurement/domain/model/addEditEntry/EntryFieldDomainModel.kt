package cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry

data class EntryFieldDomainModel(
    val fieldId: String,
    val name: String,
    val value: String? = null,
)
