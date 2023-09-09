package cz.vvoleman.phr.feature_medicine.presentation.model.list

data class SubstanceAmountPresentationModel(
    val substance: SubstancePresentationModel,
    val amount: String,
    val unit: String
)
