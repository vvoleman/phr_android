package cz.vvoleman.phr.common.domain.event.problemCategory

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

data class GetProblemCategoriesAdditionalInfoEvent(
    val problemCategories: List<ProblemCategoryDomainModel>,
)
