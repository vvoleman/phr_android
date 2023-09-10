package cz.vvoleman.phr.featureMedicine.domain.model.medicine

data class SubstanceAmountDomainModel(
    val substance: SubstanceDomainModel,
    val amount: String,
    val unit: String
)
