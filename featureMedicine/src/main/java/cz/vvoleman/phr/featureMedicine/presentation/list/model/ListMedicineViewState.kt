package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.common.presentation.model.GroupedItemsPresentationModel
import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicinePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.model.list.ScheduleItemWithDetailsPresentationModel

data class ListMedicineViewState(
    val patient: PatientPresentationModel? = null,
    val nextSchedules: List<ScheduleItemWithDetailsPresentationModel> = emptyList(),
    val selectedNextSchedule: NextScheduleItemPresentationModel? = null,
    val timelineSchedules: List<GroupedItemsPresentationModel<ScheduleItemWithDetailsPresentationModel>> = emptyList(),
    val catalogueSchedules: List<GroupedItemsPresentationModel<MedicineSchedulePresentationModel>> = emptyList(),
    val medicines: List<MedicinePresentationModel> = emptyList()
)
