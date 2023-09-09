package cz.vvoleman.phr.feature_medicine.domain.repository

import cz.vvoleman.phr.feature_medicine.domain.model.schedule.MedicineScheduleDomainModel

interface GetScheduleByMedicineRepository {

    suspend fun getScheduleByMedicine(medicineId: String, patientId: String): List<MedicineScheduleDomainModel>
}
