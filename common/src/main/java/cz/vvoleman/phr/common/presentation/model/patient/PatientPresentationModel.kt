package cz.vvoleman.phr.common.presentation.model.patient

import java.time.LocalDate

data class PatientPresentationModel(
    val id: String,
    val name: String,
    val birthDate: LocalDate? = null
)
