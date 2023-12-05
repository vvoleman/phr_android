package cz.vvoleman.phr.common.presentation.model.healthcare.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalServiceWithWorkersPresentationModel(
    val medicalService: MedicalServicePresentationModel,
    val workers: List<MedicalWorkerWithInfoPresentationModel>
) : Parcelable
