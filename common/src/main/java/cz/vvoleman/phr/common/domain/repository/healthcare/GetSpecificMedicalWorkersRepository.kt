package cz.vvoleman.phr.common.domain.repository.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel

interface GetSpecificMedicalWorkersRepository {

    suspend fun getSpecificMedicalWorkers(workerId: String): List<SpecificMedicalWorkerDomainModel>

}
