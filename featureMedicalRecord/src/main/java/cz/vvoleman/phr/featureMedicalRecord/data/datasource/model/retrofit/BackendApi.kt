package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.diagnose.DiagnoseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BackendApi {

    companion object {
        const val BASE_URL = "http://vvoleman.eu:9999/api/"
    }

    @GET("diagnose")
    suspend fun searchDiagnoses(
        @Query("search") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): DiagnoseResponse

    @GET("diagnose/multiple")
    suspend fun getDiagnosesByIds(
        @Query("ids[]") ids: Set<String>
    ): DiagnoseResponse
}
