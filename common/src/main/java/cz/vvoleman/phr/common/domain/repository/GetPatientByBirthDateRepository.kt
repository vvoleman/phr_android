package cz.vvoleman.phr.common.domain.repository

import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import java.time.LocalDate

interface GetPatientByBirthDateRepository {

    suspend fun getPatientByBirthDate(birthDate: LocalDate): List<PatientDomainModel>
}
