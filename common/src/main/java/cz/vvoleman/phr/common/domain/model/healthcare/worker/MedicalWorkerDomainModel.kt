package cz.vvoleman.phr.common.domain.model.healthcare.worker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalWorkerDomainModel(
    val id: String?,
    val name: String,
    val patientId: String,
) : Parcelable
