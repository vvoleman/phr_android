package cz.vvoleman.phr.featureMedicine.presentation.list.model

data class GroupedMedicineSchedulesPresentationModel(
    val groupLabel: String,
    val schedules: List<MedicineSchedulePresentationModel>
)
