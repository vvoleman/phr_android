package cz.vvoleman.phr.feature_medicine.domain.model.medicine

data class PackagingDomainModel(
    val form: ProductFormDomainModel,
    val packaging: String,
)
