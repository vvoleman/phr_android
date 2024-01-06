package cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class AddEditProblemCategoryDestination: PresentationDestination {
    data class Saved(val savedId: String) : AddEditProblemCategoryDestination()
}
