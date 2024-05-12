package cz.vvoleman.phr.common.ui.mapper.problemCategory.destination

import android.content.Context
import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.common.presentation.model.problemCategory.list.ListProblemCategoryDestination
import cz.vvoleman.phr.common.ui.view.problemCategory.list.ListProblemCategoryFragmentDirections
import cz.vvoleman.phr.common.utils.capitalize
import cz.vvoleman.phr.common_datasource.R

class ListProblemCategoryDestinationUiMapper(
    private val context: Context,
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val det = destination as ListProblemCategoryDestination) {
            ListProblemCategoryDestination.AddProblemCategory -> {
                navigateAddEdit(R.string.action_add)
            }

            is ListProblemCategoryDestination.EditProblemCategory -> {
                Log.d("ListProblemCategoryDestinationUiMapper", "EditProblemCategory: ${det.id}")
                navigateAddEdit(R.string.action_edit, det.id)
            }

            is ListProblemCategoryDestination.OpenDetail -> {
                val action =
                    ListProblemCategoryFragmentDirections
                        .actionListProblemCategoryFragmentToDetailProblemCategoryFragment(
                            det.id
                        )
                navManager.navigate(action)
            }
        }
    }

    private fun navigateAddEdit(action: Int, id: String? = null) {
        val actionString = context.getString(action).capitalize()
        navManager.navigate(
            ListProblemCategoryFragmentDirections
                .actionListProblemCategoryFragmentToAddEditProblemCategoryFragment(
                    action = actionString,
                    problemCategoryId = id
                )
        )
    }
}
