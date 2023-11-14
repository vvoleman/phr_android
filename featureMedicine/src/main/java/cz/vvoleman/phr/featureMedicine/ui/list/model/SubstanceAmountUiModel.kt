package cz.vvoleman.phr.featureMedicine.ui.list.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubstanceAmountUiModel(
    val substance: SubstanceUiModel,
    val amount: String,
    val unit: String
) : Parcelable
