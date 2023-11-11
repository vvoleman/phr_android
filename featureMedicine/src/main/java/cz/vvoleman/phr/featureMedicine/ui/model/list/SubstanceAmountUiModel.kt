package cz.vvoleman.phr.featureMedicine.ui.model.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubstanceAmountUiModel(
    val substance: SubstanceUiModel,
    val amount: String,
    val unit: String
) : Parcelable
