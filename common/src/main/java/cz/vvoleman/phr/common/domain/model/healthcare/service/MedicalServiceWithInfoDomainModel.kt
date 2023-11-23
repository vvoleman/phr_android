package cz.vvoleman.phr.common.domain.model.healthcare.service

data class MedicalServiceWithInfoDomainModel(
    val medicalService: MedicalServiceDomainModel,
    val email: String? = null,
    val telephone: String? = null,
)
