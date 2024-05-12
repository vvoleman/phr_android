package cz.vvoleman.phr.common.presentation.model.problemCategory

import android.os.Parcelable
import cz.vvoleman.phr.common.presentation.model.healthcare.core.AdditionalInfoPresentationModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProblemCategoryInfoPresentationModel(
    val mainSlot: Pair<Number, String>,
    val secondarySlots: List<AdditionalInfoPresentationModel<ProblemCategoryPresentationModel>>
) : Parcelable
