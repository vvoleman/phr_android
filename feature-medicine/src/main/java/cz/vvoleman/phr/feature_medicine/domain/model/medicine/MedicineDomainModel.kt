package cz.vvoleman.phr.feature_medicine.domain.model.medicine

data class MedicineDomainModel(
    val id: String,
    val name: String,
    val packaging: PackagingDomainModel,
    val country: String,
    val substances: List<SubstanceAmountDomainModel>
)
