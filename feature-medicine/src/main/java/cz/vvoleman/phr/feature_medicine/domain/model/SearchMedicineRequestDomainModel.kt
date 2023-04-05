package cz.vvoleman.phr.feature_medicine.domain.model

data class SearchMedicineRequestDomainModel(
    val query: String,
    val page: Int = 0
)
