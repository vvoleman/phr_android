package cz.vvoleman.phr.common.domain.repository.healthcare

interface RemoveSpecificMedicalWorkerRepository {

    suspend fun removeSpecificMedicalWorker(specificWorkerId: String)

    suspend fun removeSpecificMedicalWorker(workerId: String, servicesId: List<String>)
}
