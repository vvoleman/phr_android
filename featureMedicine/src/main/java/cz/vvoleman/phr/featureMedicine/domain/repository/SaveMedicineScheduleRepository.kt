package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.save.SaveMedicineScheduleDomainModel

interface SaveMedicineScheduleRepository {

    suspend fun saveMedicineSchedule(medicineSchedule: SaveMedicineScheduleDomainModel): MedicineScheduleDomainModel
}
