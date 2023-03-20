package cz.vvoleman.phr.feature_medicalrecord.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProblemCategoryDomainModel(
    val id: String,
    val name: String,
    val color: String,
    val patientId: String,
) : Parcelable
