package cz.vvoleman.phr.featureMedicine.domain.model

data class SearchMedicineRequestDomainModel(
    val query: String,
    val page: Int = 0
)
