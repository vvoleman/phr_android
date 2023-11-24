package cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare

import retrofit2.http.GET
import retrofit2.http.Query

interface HealthcareApi {

    @GET("healthcare/list")
    suspend fun getFacilities(
        @Query("page") page: Int,
        @Query("full_name") fullName: String = "",
        @Query("city") city: String = "",
    ): HealthcareResponse
}
