package cz.vvoleman.phr.featureMedicalRecord.ui.mapper

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditDestination
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.AddEditMedicalRecordsFragmentDirections

class AddEditDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            is AddEditDestination.RecordSaved -> {
                val action = AddEditMedicalRecordsFragmentDirections
                    .actionAddEditMedicalRecordsFragmentToListMedicalRecordsFragment()
                navManager.navigate(action)
            }

            is AddEditDestination.AddRecordFile -> {
                val action =
                    AddEditMedicalRecordsFragmentDirections
                        .actionAddEditMedicalRecordsFragmentToSelectFileFragment(
                            destination.previousViewState
                        )
                navManager.navigate(action)
            }
        }
    }
}
