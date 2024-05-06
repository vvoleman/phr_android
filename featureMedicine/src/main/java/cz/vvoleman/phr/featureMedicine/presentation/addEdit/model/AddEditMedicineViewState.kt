package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

import androidx.paging.PagingData
import cz.vvoleman.phr.common.presentation.model.frequencySelector.FrequencyDayPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicinePresentationModel
import kotlinx.coroutines.flow.Flow

data class AddEditMedicineViewState(
    val scheduleId: String? = null,
    val medicines: List<MedicinePresentationModel> = emptyList(),
    val medicineQuery: String = "",
    val medicineStream: Flow<PagingData<MedicinePresentationModel>>? = null,
    val selectedMedicine: MedicinePresentationModel? = null,
    val times: List<TimePresentationModel> = emptyList(),
    val frequencyDays: List<FrequencyDayPresentationModel> = emptyList(),
    val patient: PatientPresentationModel? = null,
    val problemCategory: ProblemCategoryPresentationModel? = null,
    val availableProblemCategories: List<ProblemCategoryPresentationModel> = emptyList(),
    val frequencyDaysDefault: List<FrequencyDayPresentationModel> = emptyList(),
)
