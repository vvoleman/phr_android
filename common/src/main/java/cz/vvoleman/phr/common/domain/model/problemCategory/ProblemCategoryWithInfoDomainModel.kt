package cz.vvoleman.phr.common.domain.model.problemCategory

data class ProblemCategoryWithInfoDomainModel(
    val problemCategory: ProblemCategoryDomainModel,
    val info: ProblemCategoryInfoDomainModel
)
