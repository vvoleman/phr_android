package cz.vvoleman.phr.common.domain.model.healthcare.worker

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecificMedicalWorkerDomainModel(
    val id: String?,
    val medicalWorker: MedicalWorkerDomainModel,
    val medicalService: MedicalServiceDomainModel,
    val email: String?,
    val telephone: String?,
) : Parcelable
