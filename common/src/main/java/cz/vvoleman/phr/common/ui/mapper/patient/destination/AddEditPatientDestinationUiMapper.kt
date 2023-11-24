package cz.vvoleman.phr.common.ui.mapper.patient.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.common.presentation.model.patient.addedit.AddEditPatientDestination
import cz.vvoleman.phr.common.ui.view.patient.addedit.AddEditPatientFragmentDirections

class AddEditPatientDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as AddEditPatientDestination) {
            is AddEditPatientDestination.Back -> {
                navManager.navigate(
                    AddEditPatientFragmentDirections.actionAddEditPatientFragmentToListPatientsFragment()
                )
            }
            is AddEditPatientDestination.PatientSaved -> {
                val action = AddEditPatientFragmentDirections.actionAddEditPatientFragmentToListPatientsFragment(
                    dest.id
                )
                navManager.navigate(action)
            }
        }
    }
}
