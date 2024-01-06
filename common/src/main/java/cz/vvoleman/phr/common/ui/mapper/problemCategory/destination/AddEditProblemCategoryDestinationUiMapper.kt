package cz.vvoleman.phr.common.ui.mapper.problemCategory.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit.AddEditProblemCategoryDestination
import cz.vvoleman.phr.common.ui.view.problemCategory.addEdit.AddEditProblemCategoryFragmentDirections

class AddEditProblemCategoryDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as AddEditProblemCategoryDestination) {
            is AddEditProblemCategoryDestination.Saved -> {
                val action = AddEditProblemCategoryFragmentDirections
                    .actionAddEditProblemCategoryFragmentToListProblemCategoryFragment(
                        savedId = dest.savedId
                    )
                navManager.navigate(action)
            }
        }
    }
}
