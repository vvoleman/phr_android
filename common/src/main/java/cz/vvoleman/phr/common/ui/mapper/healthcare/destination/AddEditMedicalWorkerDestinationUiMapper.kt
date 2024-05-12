package cz.vvoleman.phr.common.ui.mapper.healthcare.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerDestination
import cz.vvoleman.phr.common.ui.view.healthcare.addEdit.AddEditMedicalWorkerFragmentDirections

class AddEditMedicalWorkerDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as AddEditMedicalWorkerDestination) {
            is AddEditMedicalWorkerDestination.Saved -> {
                val action = AddEditMedicalWorkerFragmentDirections
                    .actionAddEditMedicalWorkerFragmentToListHealthcareFragment(
                        savedId = dest.id
                    )
                navManager.navigate(action)
            }
        }
    }
}
