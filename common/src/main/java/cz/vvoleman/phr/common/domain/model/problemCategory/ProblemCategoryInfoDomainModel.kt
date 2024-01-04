package cz.vvoleman.phr.common.domain.model.problemCategory

import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel

data class ProblemCategoryInfoDomainModel(
    val mainSlot: Pair<Number, String>,
    val secondarySlots: List<AdditionalInfoDomainModel<ProblemCategoryDomainModel>>,
    val priority: Int = 1
)
