package cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine

data class MedicineResponse(
    val data: List<MedicineApiDataSourceModel>
) {
    companion object {
        const val PER_PAGE = 10
    }
}
