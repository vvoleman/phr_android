package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel

data class ScheduleItemWithDetailsPresentationModel(
    val scheduleItem: ScheduleItemPresentationModel,
    val medicine: MedicinePresentationModel,
    val patient: PatientPresentationModel,
    val medicineScheduleId: String,
    val isAlarmEnabled: Boolean
)
