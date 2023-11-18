package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

interface ScheduleMedicineRepository {

    fun scheduleMedicine(medicineSchedule: MedicineScheduleDomainModel): Boolean
}
