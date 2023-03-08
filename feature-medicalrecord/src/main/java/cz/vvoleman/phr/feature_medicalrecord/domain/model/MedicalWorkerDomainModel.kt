package cz.vvoleman.phr.feature_medicalrecord.domain.model
data class MedicalWorkerDomainModel(
    val id: String,
    val name: String,
    val email: String? = null,
    val phone: String? = null,
    val address: AddressDomainModel? = null
)
