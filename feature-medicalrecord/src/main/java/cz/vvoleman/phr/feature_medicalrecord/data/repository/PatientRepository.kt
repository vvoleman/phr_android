package cz.vvoleman.phr.feature_medicalrecord.data.repository

import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.PatientDataSourceToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetSelectedPatientRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class PatientRepository(
    private val patientDataStore: PatientDataStore,
    private val patientDao: PatientDao,
    private val patientMapper: PatientDataSourceToDomainMapper
) : GetSelectedPatientRepository {

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

}