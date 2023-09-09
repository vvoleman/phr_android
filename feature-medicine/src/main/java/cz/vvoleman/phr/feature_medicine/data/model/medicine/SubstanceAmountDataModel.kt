package cz.vvoleman.phr.feature_medicine.data.model.medicine

data class SubstanceAmountDataModel(
    val substance: SubstanceDataModel,
    val amount: String,
    val unit: String
)
