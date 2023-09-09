package cz.vvoleman.phr.api.backend

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
}
