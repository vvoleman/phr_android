package cz.vvoleman.phr.featureMedicalRecord.ui.mapper

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile.SelectFileDestination
import cz.vvoleman.phr.featureMedicalRecord.ui.view.selectFile.SelectFileFragmentDirections

class SelectFileDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {
    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            is SelectFileDestination.SuccessWithOptions -> {
                val action =
                    SelectFileFragmentDirections.actionSelectFileFragmentToAddEditMedicalRecordsFragment(
                        previousViewState = destination.parentViewState,
                        fileAsset = destination.fileAsset,
                        selectedOptions = destination.selectedOptions
                    )
                navManager.navigate(action)
            }
            is SelectFileDestination.Success -> {
                val action =
                    SelectFileFragmentDirections.actionSelectFileFragmentToAddEditMedicalRecordsFragment(
                        previousViewState = destination.parentViewState,
                        fileAsset = destination.fileAsset
                    )
                navManager.navigate(action)
            }
            is SelectFileDestination.Cancel -> {
                val action =
                    SelectFileFragmentDirections.actionSelectFileFragmentToAddEditMedicalRecordsFragment()
                navManager.navigate(action)
            }
        }
    }
}
