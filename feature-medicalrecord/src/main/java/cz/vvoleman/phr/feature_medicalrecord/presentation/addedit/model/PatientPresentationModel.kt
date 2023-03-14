package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import java.time.LocalDate

data class PatientPresentationModel (
    val id: String,
    val name: String,
    val birthDate: LocalDate? = null
)