package cz.vvoleman.phr.common.data.repository.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDao
import cz.vvoleman.phr.common.data.mapper.healthcare.SpecificMedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.DeleteMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.RemoveSpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveSpecificMedicalWorkerRepository
import kotlinx.coroutines.flow.firstOrNull

class SpecificMedicalWorkerRepository(
    private val medicalWorkerDao: MedicalWorkerDao,
    private val specificMedicalWorkerDao: SpecificMedicalWorkerDao,
    private val specificMapper: SpecificMedicalWorkerDataSourceToDomainMapper,
) : GetSpecificMedicalWorkersRepository,
    SaveSpecificMedicalWorkerRepository,
    RemoveSpecificMedicalWorkerRepository,
    DeleteMedicalWorkerRepository {

    override suspend fun getSpecificMedicalWorkers(workerId: String): List<SpecificMedicalWorkerDomainModel> {
        return specificMedicalWorkerDao
            .getByMedicalWorker(workerId)
            .firstOrNull()
            ?.map {
                specificMapper.toDomain(it)
            } ?: emptyList()
    }

    override suspend fun getSpecificMedicalWorkersByFacility(facilityId: String): List<SpecificMedicalWorkerDomainModel> {
        return specificMedicalWorkerDao
            .getByFacility(facilityId)
            .firstOrNull()
            ?.map {
                specificMapper.toDomain(it)
            } ?: emptyList()
    }

    override suspend fun removeSpecificMedicalWorker(specificWorkerId: String) {
        specificMedicalWorkerDao.delete(specificWorkerId)
    }

    override suspend fun removeSpecificMedicalWorker(workerId: String, servicesId: List<String>) {
        specificMedicalWorkerDao.delete(workerId, servicesId)
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

    override suspend fun deleteMedicalWorker(worker: MedicalWorkerDomainModel) {
        specificMedicalWorkerDao.deleteByMedicalWorker(worker.id!!)

        medicalWorkerDao.delete(worker.id.toInt())
    }

    override suspend fun getSpecificMedicalWorkersByPatient(patientId: String): List<SpecificMedicalWorkerDomainModel> {
        return specificMedicalWorkerDao
            .getByPatient(patientId)
            .firstOrNull()
            ?.map {
                specificMapper.toDomain(it)
            } ?: emptyList()
    }
}
