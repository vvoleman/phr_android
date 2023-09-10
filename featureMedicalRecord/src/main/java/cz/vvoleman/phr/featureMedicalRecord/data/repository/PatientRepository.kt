package cz.vvoleman.phr.featureMedicalRecord.data.repository

import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.GetPatientByBirthDateRepository
import kotlinx.coroutines.flow.*
import java.time.LocalDate

class PatientRepository(
    private val patientDao: PatientDao,
    private val patientMapper: PatientDataSourceModelToDomainMapper
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
