package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import java.time.LocalDateTime

interface MarkMedicineScheduleFinishedRepository {

    suspend fun markMedicineScheduleFinished(medicineSchedule: MedicineScheduleDomainModel, endingAt: LocalDateTime)
}
