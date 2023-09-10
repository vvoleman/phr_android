package cz.vvoleman.phr.featureMedicine.domain.repository.timeline

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

interface GetSchedulesByPatientRepository {

    suspend fun getSchedulesByPatient(patientId: String): List<MedicineScheduleDomainModel>
}
