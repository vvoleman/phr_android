package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import android.os.Parcelable
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.DiagnosePresentationModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.PatientPresentationModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class SelectedOptionsPresentationModel(
    val patient: PatientPresentationModel? = null,
    val visitDate: LocalDate? = null,
    val diagnose: DiagnosePresentationModel? = null
) : Parcelable
