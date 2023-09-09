package cz.vvoleman.phr.feature_medicalrecord.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiagnoseDomainModel(
    val id: String,
    val name: String,
    val parent: String
) : Parcelable
