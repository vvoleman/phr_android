package cz.vvoleman.phr.common.domain.repository.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel

interface SaveSpecificMedicalWorkerRepository {

    suspend fun saveSpecificMedicalWorker(specificWorker: SpecificMedicalWorkerDomainModel): String

    suspend fun saveSpecificMedicalWorker(specificWorkers: List<SpecificMedicalWorkerDomainModel>): List<String>

}
