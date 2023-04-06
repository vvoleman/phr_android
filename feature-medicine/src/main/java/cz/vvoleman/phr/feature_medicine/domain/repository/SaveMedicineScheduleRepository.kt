package cz.vvoleman.phr.feature_medicine.domain.repository

import cz.vvoleman.phr.feature_medicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.feature_medicine.domain.model.schedule.save.SaveMedicineScheduleDomainModel

interface SaveMedicineScheduleRepository {

    suspend fun saveMedicineSchedule(medicineSchedule: SaveMedicineScheduleDomainModel): MedicineScheduleDomainModel

}