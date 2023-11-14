package cz.vvoleman.phr.featureMedicine.presentation.list.model

data class SubstanceAmountPresentationModel(
    val substance: SubstancePresentationModel,
    val amount: String,
    val unit: String
)
