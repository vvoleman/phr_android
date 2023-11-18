package cz.vvoleman.phr.featureMedicine.domain.repository.timeline

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

interface GetNextScheduledRepository {

    suspend fun getNextScheduled(patientId: String, limit: Int = 5): List<MedicineScheduleDomainModel>
}
