package cz.vvoleman.phr.featureMedicalRecord.data.repository

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.mapper.healthcare.MedicalWorkerDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.healthcare.SpecificMedicalWorkerDataSourceToDomainMapper
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalWorkersForPatientRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetUsedMedicalWorkersRepository
import kotlinx.coroutines.flow.first

class MedicalWorkerRepository(
    private val medicalRecordDao: MedicalRecordDao,
    private val medicalWorkerDao: MedicalWorkerDao,
    private val medicalWorkerMapper: MedicalWorkerDataSourceModelToDomainMapper,
    private val specificWorkerMapper: SpecificMedicalWorkerDataSourceToDomainMapper
) : GetUsedMedicalWorkersRepository, GetMedicalWorkersForPatientRepository {

    override suspend fun getUsedMedicalWorkers(patientId: String): List<SpecificMedicalWorkerDomainModel> {
        return medicalRecordDao.getUsedWorkersByPatientId(patientId).first()
            .map { specificWorkerMapper.toDomain(it) }
    }

    override suspend fun getMedicalWorkersForPatient(patientId: String): List<MedicalWorkerDomainModel> {
        return medicalWorkerDao.getAll(patientId).first()
            .map { medicalWorkerMapper.toDomain(it) }
    }
}
