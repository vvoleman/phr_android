package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalWorkerPresentationModel(
    val id: String,
    val name: String
) : Parcelable
