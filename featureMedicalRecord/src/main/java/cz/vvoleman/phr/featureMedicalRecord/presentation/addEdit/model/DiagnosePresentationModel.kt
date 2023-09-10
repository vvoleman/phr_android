package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiagnosePresentationModel(
    val id: String,
    val name: String,
    val parent: String
) : Parcelable
