package cz.vvoleman.phr.common.domain.repository.healthcare

import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithServicesDomainModel

interface GetMedicalWorkersWithServicesRepository {

    suspend fun getMedicalWorkersWithServices(patientId: String): List<MedicalWorkerWithServicesDomainModel>

    suspend fun getMedicalWorkerWithServices(medicalWorkerId: String): MedicalWorkerWithServicesDomainModel?
}
