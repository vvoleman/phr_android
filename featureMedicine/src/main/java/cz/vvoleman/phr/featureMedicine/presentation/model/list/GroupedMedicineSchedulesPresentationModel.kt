package cz.vvoleman.phr.featureMedicine.presentation.model.list

data class GroupedMedicineSchedulesPresentationModel(
    val groupLabel: String,
    val schedules: List<MedicineSchedulePresentationModel>
)
