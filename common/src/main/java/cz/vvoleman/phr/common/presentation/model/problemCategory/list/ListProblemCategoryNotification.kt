package cz.vvoleman.phr.common.presentation.model.problemCategory.list

import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel

sealed class ListProblemCategoryNotification {
    data class DeleteFailed(val problemCategory: ProblemCategoryPresentationModel) : ListProblemCategoryNotification()
    object Deleted : ListProblemCategoryNotification()
}
