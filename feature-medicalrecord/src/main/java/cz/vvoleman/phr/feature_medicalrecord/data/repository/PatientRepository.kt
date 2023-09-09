package cz.vvoleman.phr.feature_medicalrecord.data.repository

import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.PatientDataSourceToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetPatientByBirthDateRepository
import kotlinx.coroutines.flow.*
import java.time.LocalDate

class PatientRepository(
    private val patientDataStore: PatientDataStore,
    private val patientDao: PatientDao,
    private val patientMapper: PatientDataSourceToDomainMapper
) : GetPatientByBirthDateRepository {

    suspend fun getPatients(): List<PatientDomainModel> {
        val patients = patientDao.getAll()

        return patients.first().map { patientMapper.toDomain(it) }
    }

    override suspend fun getPatientByBirthDate(birthDate: LocalDate): List<PatientDomainModel> {
        val patients = patientDao.getByBirthDate(birthDate)

        return patients.first().map { patientMapper.toDomain(it) }
    }
}
