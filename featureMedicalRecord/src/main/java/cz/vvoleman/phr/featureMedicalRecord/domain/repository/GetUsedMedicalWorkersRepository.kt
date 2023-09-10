package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalWorkerDomainModel

interface GetUsedMedicalWorkersRepository {

    suspend fun getUsedMedicalWorkers(patientId: String): List<MedicalWorkerDomainModel>
}
