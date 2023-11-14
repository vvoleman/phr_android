package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.common.presentation.model.GroupedItemsPresentationModel
import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel

data class ListMedicineViewState(
    val patient: PatientPresentationModel? = null,
    val nextSchedules: List<ScheduleItemWithDetailsPresentationModel> = emptyList(),
    val selectedNextSchedule: NextScheduleItemPresentationModel? = null,
    val timelineSchedules: List<GroupedItemsPresentationModel<ScheduleItemWithDetailsPresentationModel>> = emptyList(),
    val medicineCatalogue: List<GroupedItemsPresentationModel<MedicineSchedulePresentationModel>> = emptyList(),
    val medicines: List<MedicinePresentationModel> = emptyList()
)
