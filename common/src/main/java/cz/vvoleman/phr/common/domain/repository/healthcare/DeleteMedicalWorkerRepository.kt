package cz.vvoleman.phr.common.domain.repository.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel

interface DeleteMedicalWorkerRepository {

    suspend fun deleteMedicalWorker(worker: MedicalWorkerDomainModel)
}
