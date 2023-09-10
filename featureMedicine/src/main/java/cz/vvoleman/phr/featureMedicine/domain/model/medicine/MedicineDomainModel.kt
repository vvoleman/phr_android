package cz.vvoleman.phr.featureMedicine.domain.model.medicine

data class MedicineDomainModel(
    val id: String,
    val name: String,
    val packaging: PackagingDomainModel,
    val country: String,
    val substances: List<SubstanceAmountDomainModel>
)
