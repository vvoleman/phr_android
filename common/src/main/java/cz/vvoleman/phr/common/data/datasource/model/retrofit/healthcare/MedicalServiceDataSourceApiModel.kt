package cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare

data class MedicalServiceDataSourceApiModel(
    val id: String,
    val careField: String,
    val careForm: String,
    val careType: String,
    val careExtent: String,
    val bedCount: Int,
    val serviceNote: String,
    val professionalRepresentative: String,
    val facilityId: String,
)
