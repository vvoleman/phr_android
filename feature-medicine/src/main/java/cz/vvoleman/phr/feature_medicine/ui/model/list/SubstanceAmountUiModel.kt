package cz.vvoleman.phr.feature_medicine.ui.model.list

data class SubstanceAmountUiModel(
    val substance: SubstanceUiModel,
    val amount: String,
    val unit: String,
)
