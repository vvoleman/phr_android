package cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile

import cz.vvoleman.phr.featureMedicalRecord.domain.model.PatientDomainModel
import java.time.LocalDate

interface GetPatientByBirthDateRepository {

    suspend fun getPatientByBirthDate(birthDate: LocalDate): List<PatientDomainModel>
}
