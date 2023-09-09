package cz.vvoleman.phr.feature_medicine.data.model.medicine

data class MedicineDataModel(
    val id: String,
    val name: String,
    val packaging: PackagingDataModel,
    val country: String,
    val substances: List<SubstanceAmountDataModel>
)
