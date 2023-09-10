package cz.vvoleman.phr.featureMedicine.data.datasource.retrofit

import cz.vvoleman.phr.featureMedicine.data.datasource.retrofit.medicine.MedicineResponse
import retrofit2.http.GET
import retrofit2.http.Query

@Suppress("FunctionParameterNaming")
interface BackendApi {

    @GET("medical-product/list")
    suspend fun searchMedicine(
        @Query("search") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = MedicineResponse.PER_PAGE
    ): MedicineResponse
}
