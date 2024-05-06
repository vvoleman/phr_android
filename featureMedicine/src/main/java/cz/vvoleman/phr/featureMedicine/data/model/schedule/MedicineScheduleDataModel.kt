package cz.vvoleman.phr.featureMedicine.data.model.schedule

import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.featureMedicine.data.model.medicine.MedicineDataModel
import java.time.LocalDateTime

data class MedicineScheduleDataModel(
    val id: String? = null,
    val patient: PatientDomainModel,
    val medicine: MedicineDataModel,
    val schedules: List<ScheduleItemDataModel>,
    val problemCategory: ProblemCategoryDomainModel? = null,
    val createdAt: LocalDateTime,
    val isAlarmEnabled: Boolean,
)
