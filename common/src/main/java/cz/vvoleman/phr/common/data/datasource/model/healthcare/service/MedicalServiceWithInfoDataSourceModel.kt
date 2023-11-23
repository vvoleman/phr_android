package cz.vvoleman.phr.common.data.datasource.model.healthcare.service

data class MedicalServiceWithInfoDataSourceModel(
    val medicalService: MedicalServiceDataSourceModel,
    val email: String? = null,
    val telephone: String? = null,
)
