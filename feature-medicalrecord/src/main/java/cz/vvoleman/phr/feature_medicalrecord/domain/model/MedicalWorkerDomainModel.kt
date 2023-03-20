package cz.vvoleman.phr.feature_medicalrecord.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalWorkerDomainModel(
    val id: String,
    val name: String,
    val patientId: String,
    val email: String? = null,
    val phone: String? = null,
    val address: AddressDomainModel? = null
) : Parcelable
