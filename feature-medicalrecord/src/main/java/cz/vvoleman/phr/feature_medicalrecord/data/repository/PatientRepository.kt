package cz.vvoleman.phr.feature_medicalrecord.data.repository

import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.PatientDataSourceToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetPatientByIdRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetSelectedPatientRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetPatientByBirthDateRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class PatientRepository(
    private val patientDataStore: PatientDataStore,
    private val patientDao: PatientDao,
    private val patientMapper: PatientDataSourceToDomainMapper
) : GetSelectedPatientRepository, GetPatientByBirthDateRepository, GetPatientByIdRepository {

    suspend fun getPatients(): List<PatientDomainModel> {
        val patients = patientDao.getAll()

        return patients.first().map { patientMapper.toDomain(it) }
    }

    suspend fun selectPatient(patientId: Int) {
        patientDataStore.updatePatient(patientId)
    }

    override fun getSelectedPatient(): Flow<PatientDomainModel> {
        return patientDataStore.preferencesFlow.flatMapLatest { preferences ->
            patientDao.getById(preferences.patientId)
        }.map {
            patientMapper.toDomain(it)
        }
    }

    override suspend fun getPatientByBirthDate(birthDate: LocalDate): List<PatientDomainModel> {
        val patients = patientDao.getByBirthDate(birthDate)

        return patients.first().map { patientMapper.toDomain(it) }
    }

    override suspend fun getPatientById(id: String): PatientDomainModel? {
        val patient = patientDao.getById(id.toInt()).firstOrNull()

        return patient?.let { patientMapper.toDomain(it) }
    }
}