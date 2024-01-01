package cz.vvoleman.phr.common.domain.repository.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel

interface SaveMedicalWorkerRepository {

    suspend fun saveMedicalWorker(worker: MedicalWorkerDomainModel): String
}
