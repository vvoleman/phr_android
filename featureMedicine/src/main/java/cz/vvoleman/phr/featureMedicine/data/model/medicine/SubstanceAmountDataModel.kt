package cz.vvoleman.phr.featureMedicine.data.model.medicine

data class SubstanceAmountDataModel(
    val substance: SubstanceDataModel,
    val amount: String,
    val unit: String
)
