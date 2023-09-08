package cz.vvoleman.phr.feature_medicine.ui.list.mapper

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineDestination
import cz.vvoleman.phr.feature_medicine.ui.list.view.ListMedicineFragmentDirections

class ListMedicineDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            is ListMedicineDestination.EditSchedule -> {
                navigateToAddEdit(destination.id)
            }
            is ListMedicineDestination.CreateSchedule -> {
                navigateToAddEdit()
            }
        }
    }

    private fun navigateToAddEdit(id: String? = null) {
        val action = ListMedicineFragmentDirections.actionListMedicineFragmentToAddEditMedicineFragment(id)
        navManager.navigate(action)
    }
}