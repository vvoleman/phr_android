package cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit

data class SearchRequestDomainModel(
    val query: String,
    val page: Int
)
