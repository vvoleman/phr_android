package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

import java.time.LocalDate

data class SelectedOptionsDomainModel(
    val diagnoseId: String? = null,
    val visitDate: LocalDate? = null,
    val patientId: String? = null
)
