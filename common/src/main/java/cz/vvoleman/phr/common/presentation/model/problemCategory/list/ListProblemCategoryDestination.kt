package cz.vvoleman.phr.common.presentation.model.problemCategory.list

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class ListProblemCategoryDestination : PresentationDestination {

    object AddProblemCategory : ListProblemCategoryDestination()

    data class EditProblemCategory(val id: String) : ListProblemCategoryDestination()

    data class OpenDetail(val id: String) : ListProblemCategoryDestination()
}
