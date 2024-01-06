package cz.vvoleman.phr.common.domain.model.problemCategory.request

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

data class DeleteProblemCategoryRequest(
    val problemCategory: ProblemCategoryDomainModel,
    val dataDeleteType: DataDeleteType
)
