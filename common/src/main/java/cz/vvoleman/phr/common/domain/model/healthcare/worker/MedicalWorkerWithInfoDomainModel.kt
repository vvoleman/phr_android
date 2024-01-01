package cz.vvoleman.phr.common.domain.model.healthcare.worker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalWorkerWithInfoDomainModel(
    val medicalWorker: MedicalWorkerDomainModel,
    val email: String? = null,
    val telephone: String? = null,
) : Parcelable
