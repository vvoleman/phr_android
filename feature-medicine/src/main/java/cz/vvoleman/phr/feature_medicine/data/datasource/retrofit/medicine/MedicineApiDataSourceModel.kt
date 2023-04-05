package cz.vvoleman.phr.feature_medicine.data.datasource.retrofit.medicine

data class MedicineApiDataSourceModel(
    val id: String,
    val name: String,
    val packaging: PackagingApiDataSourceModel,
    val substances: List<SubstanceApiDataSourceModel>,
    val country: String
)
