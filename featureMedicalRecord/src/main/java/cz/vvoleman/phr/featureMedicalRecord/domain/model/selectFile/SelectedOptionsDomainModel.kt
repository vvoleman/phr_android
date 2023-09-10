package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile

import java.time.LocalDate

data class SelectedOptionsDomainModel(
    val diagnoseId: String? = null,
    val visitDate: LocalDate? = null,
    val patientId: String? = null
)
