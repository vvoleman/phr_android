package cz.vvoleman.phr.common.ui.mapper.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsDestination
import cz.vvoleman.phr.common.ui.view.listpatients.ListPatientsFragmentDirections

class ListPatientsDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as ListPatientsDestination) {
            is ListPatientsDestination.AddPatient -> {
                val action = ListPatientsFragmentDirections.actionListPatientsFragmentToAddEditPatientFragment()
                navManager.navigate(action)
            }
            is ListPatientsDestination.EditPatient -> {
                val action = ListPatientsFragmentDirections.actionListPatientsFragmentToAddEditPatientFragment(patientId = dest.id)
                navManager.navigate(action)
            }
            is ListPatientsDestination.PatientSelected -> TODO()
        }
    }
}