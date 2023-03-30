package cz.vvoleman.phr.common.ui.model

import java.time.LocalDate

data class PatientUiModel(
    val id: String,
    val name: String,
    val birthDate: LocalDate? = null,
    val isSelected: Boolean = false
)
