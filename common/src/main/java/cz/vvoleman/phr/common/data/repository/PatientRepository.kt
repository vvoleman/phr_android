package cz.vvoleman.phr.common.data.repository

import android.util.Log
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToAddEditMapper
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.domain.model.AddEditPatientDomainModel
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.common.domain.repository.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class PatientRepository(
    private val patientDao: PatientDao,
    private val patientDataStore: PatientDataStore,
    private val patientDomainModelToDataSourceMapper: PatientDataSourceModelToDomainMapper,
    private val patientDataSourceModelToAddEditMapper: PatientDataSourceModelToAddEditMapper
) : GetPatientByIdRepository,
    SavePatientRepository,
    GetAllPatientsRepository,
    GetSelectedPatientRepository,
    SwitchSelectedPatientRepository,
    DeletePatientRepository {

    override suspend fun getAll(): List<PatientDomainModel> {
        return patientDao.getAll().first().map { patientDomainModelToDataSourceMapper.toDomain(it) }
    }

    override suspend fun getById(id: String): PatientDomainModel? {
        return patientDao.getById(id).firstOrNull()
            ?.let { patientDomainModelToDataSourceMapper.toDomain(it) }
    }

    override suspend fun savePatient(patient: AddEditPatientDomainModel): String {
        val model = patientDataSourceModelToAddEditMapper.toDataSource(patient)
        return patientDao.insert(model)
            .toString()
    }

    override fun getSelectedPatient(): Flow<PatientDomainModel> {
        return patientDataStore.preferencesFlow.flatMapLatest { preferences ->
            patientDao.getById(preferences.patientId)
        }.map {
            patientDomainModelToDataSourceMapper.toDomain(it)
        }
    }

    override suspend fun switchSelectedPatient(patientId: String) {
        patientDataStore.updatePatient(patientId)
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun deletePatient(id: String): Boolean {
        val patient = patientDao.getById(id).firstOrNull() ?: return false
        return try {
            patientDao.delete(patient)
            true
        } catch (e: Exception) {
            Log.e("PatientRepository", "Error deleting patient", e)
            false
        }
    }
}
