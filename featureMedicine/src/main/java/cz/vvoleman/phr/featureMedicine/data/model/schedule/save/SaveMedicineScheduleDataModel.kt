package cz.vvoleman.phr.featureMedicine.data.model.schedule.save

import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.data.model.medicine.MedicineDataModel
import cz.vvoleman.phr.featureMedicine.data.model.schedule.ScheduleItemDataModel
import java.time.LocalDateTime

data class SaveMedicineScheduleDataModel(
    val id: String? = null,
    val patient: PatientDomainModel,
    val medicine: MedicineDataModel,
    val schedules: List<ScheduleItemDataModel>,
    val createdAt: LocalDateTime,
)
