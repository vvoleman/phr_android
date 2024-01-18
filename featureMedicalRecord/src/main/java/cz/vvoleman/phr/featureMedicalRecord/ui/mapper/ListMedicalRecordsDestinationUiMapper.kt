package cz.vvoleman.phr.featureMedicalRecord.ui.mapper

import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.list.ListMedicalRecordDestination
import cz.vvoleman.phr.featureMedicalRecord.ui.view.list.ListMedicalRecordFragmentDirections

class ListMedicalRecordsDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {
    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            is ListMedicalRecordDestination.NewMedicalRecord -> {
                val action = ListMedicalRecordFragmentDirections
                    .actionListMedicalRecordsFragmentToAddEditMedicalRecordsFragment(
                        null
                    )
                Log.d("ListMedicalRecordsDestinationUiMapper", "NewMedicalRecord")
                navManager.navigate(action)
            }

            is ListMedicalRecordDestination.EditMedicalRecord -> {
                val action = ListMedicalRecordFragmentDirections
                    .actionListMedicalRecordsFragmentToAddEditMedicalRecordsFragment(
                        destination.id
                    )
                navManager.navigate(action)
            }
        }
    }
}
