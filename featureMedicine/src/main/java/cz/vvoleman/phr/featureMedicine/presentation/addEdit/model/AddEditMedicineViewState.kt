package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

import cz.vvoleman.phr.common.presentation.model.frequencySelector.FrequencyDayPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicinePresentationModel

data class AddEditMedicineViewState(
    val scheduleId: String? = null,
    val medicines: List<MedicinePresentationModel> = emptyList(),
    val selectedMedicine: MedicinePresentationModel? = null,
    val times: List<TimePresentationModel> = emptyList(),
    val frequencyDays: List<FrequencyDayPresentationModel> = emptyList(),
    val patient: PatientPresentationModel? = null,
    val frequencyDaysDefault: List<FrequencyDayPresentationModel> = emptyList(),
)
