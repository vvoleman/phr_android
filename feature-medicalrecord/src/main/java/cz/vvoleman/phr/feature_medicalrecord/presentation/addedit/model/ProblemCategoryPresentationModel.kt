package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProblemCategoryPresentationModel(
    val id: String,
    val name: String,
    val color: String
) : Parcelable
