package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

interface GetSchedulesByMedicineRepository {

    suspend fun getSchedulesByMedicine(medicineIds: List<String>, patientId: String): List<MedicineScheduleDomainModel>
}
