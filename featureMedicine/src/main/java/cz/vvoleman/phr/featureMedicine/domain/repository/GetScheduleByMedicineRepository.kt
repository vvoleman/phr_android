package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

interface GetScheduleByMedicineRepository {

    suspend fun getScheduleByMedicine(medicineId: String, patientId: String): List<MedicineScheduleDomainModel>
}
