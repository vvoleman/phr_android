package cz.vvoleman.phr.featureMedicine.domain.model.schedule

import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel

data class ScheduleItemWithDetailsDomainModel(
    val scheduleItem: ScheduleItemDomainModel,
    val medicine: MedicineDomainModel,
    val patient: PatientDomainModel,
    val medicineScheduleId: String,
    val isAlarmEnabled: Boolean
)
