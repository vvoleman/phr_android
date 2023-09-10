package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProblemCategoryPresentationModel(
    val id: String,
    val name: String,
    val color: String
) : Parcelable
