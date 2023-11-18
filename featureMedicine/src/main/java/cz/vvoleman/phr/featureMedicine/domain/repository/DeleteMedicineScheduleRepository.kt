package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

interface DeleteMedicineScheduleRepository {

    suspend fun deleteMedicineSchedule(medicineSchedule: MedicineScheduleDomainModel): Boolean
}
