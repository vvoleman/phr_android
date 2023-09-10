package cz.vvoleman.phr.featureMedicine.data.model.medicine

data class MedicineDataModel(
    val id: String,
    val name: String,
    val packaging: PackagingDataModel,
    val country: String,
    val substances: List<SubstanceAmountDataModel>
)
