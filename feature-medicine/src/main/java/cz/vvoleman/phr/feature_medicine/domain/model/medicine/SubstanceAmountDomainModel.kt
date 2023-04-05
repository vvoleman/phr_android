package cz.vvoleman.phr.feature_medicine.domain.model.medicine

data class SubstanceAmountDomainModel(
    val substance: SubstanceDomainModel,
    val amount: String,
    val unit: String,
)
