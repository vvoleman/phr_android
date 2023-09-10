package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicinePresentationModel

data class AddEditMedicineViewState(
    val medicines: List<MedicinePresentationModel> = emptyList(),
    val times: List<TimePresentationModel> = emptyList()
)
