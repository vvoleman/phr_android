package cz.vvoleman.phr.common.domain.model.healthcare.worker

data class MedicalWorkerAdditionalInfoDomainModel(
    val icon: Int? = null,
    val text: String,
    val onClick: ((MedicalWorkerDomainModel) -> Unit)? = null
)
