package cz.vvoleman.phr.common.ui.model.healthcare.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalWorkerUiModel(
    val id: String,
    val name: String,
    val patientId: String
) : Parcelable
