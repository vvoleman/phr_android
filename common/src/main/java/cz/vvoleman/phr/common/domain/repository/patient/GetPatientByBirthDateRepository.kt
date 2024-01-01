package cz.vvoleman.phr.common.domain.repository.patient

import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import java.time.LocalDate

interface GetPatientByBirthDateRepository {
    suspend fun getPatientByBirthDate(birthDate: LocalDate): List<PatientDomainModel>
}
