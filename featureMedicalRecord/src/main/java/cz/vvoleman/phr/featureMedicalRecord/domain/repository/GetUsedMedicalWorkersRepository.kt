package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel

interface GetUsedMedicalWorkersRepository {

    suspend fun getUsedMedicalWorkers(patientId: String): List<SpecificMedicalWorkerDomainModel>
}
