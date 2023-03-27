package cz.vvoleman.phr.feature_medicalrecord.ui.mapper

import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditDestination
import cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit.AddEditMedicalRecordsFragmentDirections

class AddEditDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            is AddEditDestination.RecordSaved -> {
                Log.d("AddEditDestinationUiMapper", "RecordSaved")
            }
            is AddEditDestination.AddRecordFile -> {
                val action = AddEditMedicalRecordsFragmentDirections.actionAddEditMedicalRecordsFragmentToSelectFileFragment()
                navManager.navigate(action)
            }
        }
    }
}