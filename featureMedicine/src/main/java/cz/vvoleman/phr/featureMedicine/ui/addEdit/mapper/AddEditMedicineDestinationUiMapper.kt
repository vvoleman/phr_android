package cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper

import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineDestination
import cz.vvoleman.phr.featureMedicine.ui.addEdit.view.AddEditMedicineFragmentDirections

class AddEditMedicineDestinationUiMapper(navManager: NavManager) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            is AddEditMedicineDestination.MedicineSaved -> {
                Log.d("AddEditMedicineDestinationUiMapper", "navigate: MedicineSaved")
                navManager.navigate(
                    AddEditMedicineFragmentDirections.actionAddEditMedicineFragmentToListMedicineFragment(
                        destination.id
                    )
                )
            }
        }
    }
}
