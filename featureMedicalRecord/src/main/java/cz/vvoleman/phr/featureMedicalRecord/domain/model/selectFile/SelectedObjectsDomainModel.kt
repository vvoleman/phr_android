package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class SelectedObjectsDomainModel(
    val patient: PatientDomainModel? = null,
    val diagnose: DiagnoseDomainModel? = null,
    val visitDate: LocalDate? = null
) : Parcelable
