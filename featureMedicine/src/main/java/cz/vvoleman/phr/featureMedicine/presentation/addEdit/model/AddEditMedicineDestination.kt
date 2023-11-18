package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class AddEditMedicineDestination : PresentationDestination {

    data class MedicineSaved(val id: String) : AddEditMedicineDestination()
}
