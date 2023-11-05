package cz.vvoleman.phr.featureMedicine.presentation.model.list

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel

data class ScheduleItemWithDetailsPresentationModel(
    val scheduleItem: ScheduleItemPresentationModel,
    val medicine: MedicinePresentationModel,
    val patient: PatientPresentationModel,
    val medicineScheduleId: String,
    val isAlarmEnabled: Boolean
)
