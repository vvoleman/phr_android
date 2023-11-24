package cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare

data class HealthcareResponse(
    val status: String,
    val data: List<MedicalFacilityDataSourceApiModel>
)
