package cz.vvoleman.phr.common.presentation.model.healthcare.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalWorkerWithInfoPresentationModel(
    val medicalWorker: MedicalWorkerPresentationModel,
    val email: String? = null,
    val telephone: String? = null,
) : Parcelable
