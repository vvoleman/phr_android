package cz.vvoleman.phr.feature_medicine.presentation.addedit.model

import cz.vvoleman.phr.feature_medicine.presentation.model.addedit.TimePresentationModel
import cz.vvoleman.phr.feature_medicine.presentation.model.list.MedicinePresentationModel

data class AddEditMedicineViewState(
    val medicines: List<MedicinePresentationModel> = emptyList(),
    val times: List<TimePresentationModel> = emptyList()
)
