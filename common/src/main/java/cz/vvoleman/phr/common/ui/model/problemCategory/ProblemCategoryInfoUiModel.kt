package cz.vvoleman.phr.common.ui.model.problemCategory

import cz.vvoleman.phr.common.ui.model.healthcare.core.AdditionalInfoUiModel

data class ProblemCategoryInfoUiModel(
    val mainSlot: Pair<Number, String>,
    val secondarySlots: List<AdditionalInfoUiModel<ProblemCategoryUiModel>>
)
