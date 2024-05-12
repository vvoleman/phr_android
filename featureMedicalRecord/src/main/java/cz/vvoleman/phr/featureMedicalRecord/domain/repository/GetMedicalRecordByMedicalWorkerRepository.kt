package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel

interface GetMedicalRecordByMedicalWorkerRepository {

    suspend fun getMedicalRecordsByMedicalWorker(
        medicalWorker: MedicalWorkerDomainModel
    ): List<MedicalRecordDomainModel>
}
