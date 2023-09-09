package cz.vvoleman.phr.feature_medicine.domain.repository.timeline

import cz.vvoleman.phr.feature_medicine.domain.model.schedule.MedicineScheduleDomainModel

interface GetSchedulesByPatientRepository {

    suspend fun getSchedulesByPatient(patientId: String): List<MedicineScheduleDomainModel>
}
