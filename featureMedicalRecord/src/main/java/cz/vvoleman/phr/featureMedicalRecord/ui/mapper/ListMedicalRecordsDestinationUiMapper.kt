package cz.vvoleman.phr.featureMedicalRecord.ui.mapper

import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.list.model.ListMedicalRecordsDestination
import cz.vvoleman.phr.featureMedicalRecord.ui.view.ListMedicalRecordsFragmentDirections

class ListMedicalRecordsDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {
    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            is ListMedicalRecordsDestination.NewMedicalRecord -> {
                val action = ListMedicalRecordsFragmentDirections
                    .actionListMedicalRecordsFragmentToAddEditMedicalRecordsFragment(
                        null
                    )
                Log.d("ListMedicalRecordsDestinationUiMapper", "NewMedicalRecord")
                navManager.navigate(action)
            }

            is ListMedicalRecordsDestination.EditMedicalRecord -> {
                val action = ListMedicalRecordsFragmentDirections
                    .actionListMedicalRecordsFragmentToAddEditMedicalRecordsFragment(
                        destination.id
                    )
                navManager.navigate(action)
            }
        }
    }
}
