package cz.vvoleman.phr.feature_medicalrecord.ui.mapper

import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectFileDestination
import cz.vvoleman.phr.feature_medicalrecord.ui.view.select_file.SelectFileFragmentDirections

class SelectFileDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {
    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            is SelectFileDestination.SuccessWithOptions -> {
                val action =
                    SelectFileFragmentDirections.actionSelectFileFragmentToAddEditMedicalRecordsFragment(
                        previousViewState = destination.parentViewState,
                        fileUri = destination.fileUri,
                        selectedOptions = destination.selectedOptions
                    )
                navManager.navigate(action)
            }
            is SelectFileDestination.Success -> {
                val action =
                    SelectFileFragmentDirections.actionSelectFileFragmentToAddEditMedicalRecordsFragment(
                        previousViewState = destination.parentViewState,
                        fileUri = destination.fileUri
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