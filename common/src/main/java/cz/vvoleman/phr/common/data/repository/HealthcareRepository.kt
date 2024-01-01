package cz.vvoleman.phr.common.data.repository

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerWithServicesDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithServicesDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalWorkerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class HealthcareRepository(
    private val medicalWorkerDao: MedicalWorkerDao,
    private val medicalWorkerWithServicesMapper: MedicalWorkerWithServicesDataSourceModelToDomainMapper,
    private val medicalWorkerMapper: MedicalWorkerDataSourceModelToDomainMapper,
) : GetMedicalWorkersWithServicesRepository,
    SaveMedicalWorkerRepository {

    override suspend fun getMedicalWorkersWithServices(patientId: String): List<MedicalWorkerWithServicesDomainModel> {
        val workers = medicalWorkerDao.getAll(patientId).first()

        return medicalWorkerWithServicesMapper.toDomain(workers)
    }

    override suspend fun getMedicalWorkerWithServices(medicalWorkerId: String): MedicalWorkerWithServicesDomainModel? {
        val worker = medicalWorkerDao.getById(medicalWorkerId.toInt()).firstOrNull()

        return worker?.let {
            medicalWorkerWithServicesMapper.toDomain(listOf(it)).firstOrNull()
        }
    }

    override suspend fun saveMedicalWorker(worker: MedicalWorkerDomainModel): String {
        val workerDataSource = medicalWorkerMapper.toDataSource(worker)
        return medicalWorkerDao.insert(workerDataSource).toString()
    }
}
