package cz.vvoleman.phr.common.presentation.event.problemCategory

import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel

data class GetProblemCategoryDetailSectionEvent(
    val problemCategory: ProblemCategoryPresentationModel,
)
