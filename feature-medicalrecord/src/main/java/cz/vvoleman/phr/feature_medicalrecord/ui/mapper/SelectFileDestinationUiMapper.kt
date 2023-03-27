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
            is SelectFileDestination.Success -> {
                Log.d("SelectFileDestinationUiMapper", "SelectFileDestination")
            }
            is SelectFileDestination.Cancel -> {
                val action = SelectFileFragmentDirections.actionSelectFileFragmentToAddEditMedicalRecordsFragment()
                navManager.navigate(action)
            }
        }
    }
}