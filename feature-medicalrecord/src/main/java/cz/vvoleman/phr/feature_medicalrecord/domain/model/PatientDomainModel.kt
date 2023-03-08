package cz.vvoleman.phr.feature_medicalrecord.domain.model

import java.time.LocalDate

data class PatientDomainModel(
    val id: String,
    val name: String,
    val birthDate: LocalDate?
)
