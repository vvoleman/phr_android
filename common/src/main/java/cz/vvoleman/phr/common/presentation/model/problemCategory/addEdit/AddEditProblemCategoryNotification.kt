package cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit

sealed class AddEditProblemCategoryNotification {
    data class MissingFields(
        val fields: List<AddEditProblemCategoryViewState.RequiredField>
    ) : AddEditProblemCategoryNotification()
}
