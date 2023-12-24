package cz.vvoleman.phr.common.presentation.model.healthcare.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalWorkerPresentationModel(
    val id: String?,
    val name: String,
    val patientId: String
) : Parcelable
