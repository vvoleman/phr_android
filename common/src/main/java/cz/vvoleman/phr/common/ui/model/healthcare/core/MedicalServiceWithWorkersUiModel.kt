package cz.vvoleman.phr.common.ui.model.healthcare.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalServiceWithWorkersUiModel(
    val medicalService: MedicalServiceUiModel,
    val workers: List<MedicalWorkerWithInfoUiModel>
) : Parcelable
