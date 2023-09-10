package cz.vvoleman.phr.featureMedicine.presentation.model.list

data class SubstanceAmountPresentationModel(
    val substance: SubstancePresentationModel,
    val amount: String,
    val unit: String
)
