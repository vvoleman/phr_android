package cz.vvoleman.phr.feature_medicine.data.model

data class SubstanceAmountDataModel(
    val substance: SubstanceDataModel,
    val amount: String,
    val unit: String,
)
