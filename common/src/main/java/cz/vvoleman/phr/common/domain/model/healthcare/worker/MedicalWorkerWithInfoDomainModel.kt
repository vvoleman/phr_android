package cz.vvoleman.phr.common.domain.model.healthcare.worker

data class MedicalWorkerWithInfoDomainModel(
    val medicalWorker: MedicalWorkerDomainModel,
    val email: String? = null,
    val telephone: String? = null,
)
