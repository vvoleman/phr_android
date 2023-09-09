package cz.vvoleman.phr.feature_medicalrecord.data.repository

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.MedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalWorkerDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetMedicalWorkersForPatientRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedMedicalWorkersRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MedicalWorkerRepository(
    private val medicalWorkerDao: MedicalWorkerDao,
    private val medicalWorkerDataSourceToDomainMapper: MedicalWorkerDataSourceToDomainMapper
) : GetUsedMedicalWorkersRepository, GetMedicalWorkersForPatientRepository {

    override suspend fun getUsedMedicalWorkers(patientId: String): List<MedicalWorkerDomainModel> {
        return medicalWorkerDao.getUsedByPatientId(patientId).first()
            .map { medicalWorkerDataSourceToDomainMapper.toDomain(it) }
    }

    override suspend fun getMedicalWorkersForPatient(patientId: String): List<MedicalWorkerDomainModel> {
        return medicalWorkerDao.getAll(patientId).first()
            .map { medicalWorkerDataSourceToDomainMapper.toDomain(it) }
    }
}
