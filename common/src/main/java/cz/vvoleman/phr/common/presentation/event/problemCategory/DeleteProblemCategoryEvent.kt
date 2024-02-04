package cz.vvoleman.phr.common.presentation.event.problemCategory

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.request.DataDeleteType

data class DeleteProblemCategoryEvent(
    val problemCategory: ProblemCategoryDomainModel,
    val deleteType: DataDeleteType
)
