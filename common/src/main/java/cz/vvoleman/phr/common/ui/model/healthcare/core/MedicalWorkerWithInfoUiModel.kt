package cz.vvoleman.phr.common.ui.model.healthcare.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalWorkerWithInfoUiModel(
    val medicalWorker: MedicalWorkerUiModel,
    val email: String? = null,
    val telephone: String? = null,
) : Parcelable
