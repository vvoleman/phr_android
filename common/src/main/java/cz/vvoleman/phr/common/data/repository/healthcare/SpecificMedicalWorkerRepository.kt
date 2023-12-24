package cz.vvoleman.phr.common.data.repository.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDao
import cz.vvoleman.phr.common.data.mapper.healthcare.SpecificMedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.RemoveSpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveSpecificMedicalWorkerRepository
import kotlinx.coroutines.flow.firstOrNull

class SpecificMedicalWorkerRepository(
    private val specificMedicalWorkerDao: SpecificMedicalWorkerDao,
    private val specificMapper: SpecificMedicalWorkerDataSourceToDomainMapper,
) : GetSpecificMedicalWorkersRepository, SaveSpecificMedicalWorkerRepository,
    RemoveSpecificMedicalWorkerRepository {

    override suspend fun getSpecificMedicalWorkers(workerId: String): List<SpecificMedicalWorkerDomainModel> {
        return specificMedicalWorkerDao
            .getByMedicalWorker(workerId)
            .firstOrNull()
            ?.map {
                specificMapper.toDomain(it)
            } ?: emptyList()
    }

    override suspend fun removeSpecificMedicalWorker(specificWorkerId: String) {
        specificMedicalWorkerDao.delete(specificWorkerId)
    }

    override suspend fun removeSpecificMedicalWorker(specificWorkerIds: List<String>) {
        specificMedicalWorkerDao.delete(specificWorkerIds)
    }

    override suspend fun saveSpecificMedicalWorker(specificWorker: SpecificMedicalWorkerDomainModel): String {
        specificMapper.toDataSource(specificWorker).let {
            return specificMedicalWorkerDao.insert(it.specificMedicalWorker).toString()
        }
    }

    override suspend fun saveSpecificMedicalWorker(
        specificWorkers: List<SpecificMedicalWorkerDomainModel>
    ): List<String> {
        return specificWorkers
            .map { specificMapper.toDataSource(it) }
            .map {
                specificMedicalWorkerDao.insert(it.specificMedicalWorker).toString()
            }
    }
}
