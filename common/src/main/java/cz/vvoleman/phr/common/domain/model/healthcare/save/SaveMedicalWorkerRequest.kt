package cz.vvoleman.phr.common.domain.model.healthcare.save

import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceWithInfoDomainModel

data class SaveMedicalWorkerRequest(
    val id: String?,
    val name: String,
    val patientId: String,
    val medicalServices: List<AddEditMedicalServiceItemDomainModel>
)
