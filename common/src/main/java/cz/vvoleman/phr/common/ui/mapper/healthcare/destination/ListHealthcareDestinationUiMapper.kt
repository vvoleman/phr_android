package cz.vvoleman.phr.common.ui.mapper.healthcare.destination

import android.content.Context
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareDestination
import cz.vvoleman.phr.common.ui.view.healthcare.list.ListHealthcareFragmentDirections
import cz.vvoleman.phr.common.utils.capitalize
import cz.vvoleman.phr.common_datasource.R

class ListHealthcareDestinationUiMapper(private val context: Context, navManager: NavManager) :
    DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as ListHealthcareDestination) {
            ListHealthcareDestination.AddMedicalWorker -> {
                navigate(R.string.action_add)
            }

            is ListHealthcareDestination.EditMedicalWorker -> {
                navigate(R.string.action_edit, dest.id)
            }

            is ListHealthcareDestination.DetailMedicalWorker -> {
                val action = ListHealthcareFragmentDirections.actionListHealthcareFragmentToDetailMedicalWorkerFragment(
                    dest.id
                )
                navManager.navigate(action)
            }
        }
    }

    private fun navigate(action: Int, id: String? = null) {
        val actionString = context.getString(action).capitalize()
        navManager.navigate(
            ListHealthcareFragmentDirections.actionListHealthcareFragmentToAddEditMedicalWorkerFragment(
                action = actionString,
                medicalWorkerId = id
            )
        )
    }
}
