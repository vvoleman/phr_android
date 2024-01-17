package cz.vvoleman.phr.featureMedicalRecord.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiagnoseGroupUiModel(
    val id: String,
    val name: String
) : Parcelable
