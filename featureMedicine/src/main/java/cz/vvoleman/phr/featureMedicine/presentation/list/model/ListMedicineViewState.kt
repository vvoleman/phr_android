package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.common.presentation.model.grouped.GroupedItemsPresentationModel
import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel

data class ListMedicineViewState(
    val patient: PatientPresentationModel? = null,
    val nextSchedules: List<ScheduleItemWithDetailsPresentationModel>,
    val selectedNextSchedule: NextSchedulePresentationModel?,
    val timelineSchedules: List<GroupedItemsPresentationModel<ScheduleItemWithDetailsPresentationModel>>,
    val medicineCatalogue: List<GroupedItemsPresentationModel<MedicineSchedulePresentationModel>>,
)
