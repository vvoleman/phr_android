package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class SelectedObjectsDomainModel(
    val patient: PatientDomainModel? = null,
    val diagnose: DiagnoseDomainModel? = null,
    val visitDate: LocalDate? = null
) : Parcelable
