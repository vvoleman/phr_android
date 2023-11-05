package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicinePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.ScheduleItemWithDetailsPresentationModel

data class ListMedicineViewState(
    val patient: PatientPresentationModel? = null,
    val nextSchedules: List<ScheduleItemWithDetailsPresentationModel> = emptyList(),
    val selectedNextSchedule: NextScheduleItemPresentationModel? = null,
    val medicines: List<MedicinePresentationModel> = emptyList()
)
