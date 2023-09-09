package cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file

import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import java.time.LocalDate

interface GetPatientByBirthDateRepository {

    suspend fun getPatientByBirthDate(birthDate: LocalDate): List<PatientDomainModel>
}
