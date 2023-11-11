package cz.vvoleman.phr.featureMedicine.domain.model.timeline

data class DeleteMedicineScheduleResult(
    val isScheduleDeleted: Boolean,
    val isAlarmDeleted: Boolean
)
