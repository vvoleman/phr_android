package cz.vvoleman.phr.featureMedicine.ui.model.list

data class MedicineUiModel(
    val id: String,
    val name: String,
    val packaging: PackagingUiModel,
    val country: String,
    val substances: List<SubstanceAmountUiModel>
)
