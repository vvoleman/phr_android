package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import java.time.LocalDate

data class SelectedOptionsDomainModel(
    val patient: PatientDomainModel? = null,
    val visitDate: LocalDate? = null,
    val diagnose: DiagnoseDomainModel? = null
)
