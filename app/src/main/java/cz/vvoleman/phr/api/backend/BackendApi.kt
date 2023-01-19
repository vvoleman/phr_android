package cz.vvoleman.phr.api.backend

import retrofit2.http.GET
import retrofit2.http.Query

interface BackendApi {

    companion object {
        const val BASE_URL = "http://vvoleman.eu:9999/api"
    }

    @GET("diagnose")
    suspend fun searchDiagnoses(
        @Query("query") query: String,
        @Query("page") page: Int
    ): DiagnoseResponse

}