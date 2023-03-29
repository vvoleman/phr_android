package cz.vvoleman.phr.common.ui.mapper.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.common.presentation.model.listpatients.ListPatientsDestination

class ListPatientsDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (destination as ListPatientsDestination) {
            is ListPatientsDestination.AddPatient -> {
//                val action = ListPatientsFragmentDirections.actionListPatientsFragmentToAddEditPatientFragment()
//                navManager.navigate(action)
            }
            is ListPatientsDestination.EditPatient -> {
//                val action = ListPatientsFragmentDirections.actionListPatientsFragmentToAddEditPatientFragment(
//                    destination.patient
//                )
//                navManager.navigate(action)
            }
            is ListPatientsDestination.PatientSelected -> TODO()
        }
    }
}