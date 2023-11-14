package cz.vvoleman.phr.featureMedicine.ui.list.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubstanceUiModel(
    val id: String,
    val name: String
): Parcelable
