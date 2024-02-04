package cz.vvoleman.phr.common.presentation.model.problemCategory.detail

import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer

data class DetailProblemCategoryViewState(
    val problemCategory: ProblemCategoryPresentationModel,
    val sections: List<SectionContainer> = emptyList()
)
