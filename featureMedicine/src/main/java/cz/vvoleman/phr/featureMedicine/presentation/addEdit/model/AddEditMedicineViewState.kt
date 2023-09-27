package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.FrequencyDayPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicinePresentationModel

data class AddEditMedicineViewState(
    val medicines: List<MedicinePresentationModel> = emptyList(),
    val selectedMedicine: MedicinePresentationModel? = null,
    val times: List<TimePresentationModel> = emptyList(),
    val frequencyDays: List<FrequencyDayPresentationModel> = emptyList(),
    val patientId: String? = null,
)
