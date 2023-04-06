package cz.vvoleman.phr.feature_medicine.data.model.schedule

import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicine.data.model.medicine.MedicineDataModel
import java.time.LocalDateTime

data class MedicineScheduleDataModel(
    val id: String? = null,
    val patient: PatientDomainModel,
    val medicine: MedicineDataModel,
    val schedules: List<ScheduleItemDataModel>,
    val createdAt: LocalDateTime
)
