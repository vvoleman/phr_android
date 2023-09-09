package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import android.os.Parcelable
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.DiagnosePresentationModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.PatientPresentationModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class RecognizedOptionsPresentationModel(
    val visitDate: List<LocalDate> = emptyList(),
    val diagnose: List<DiagnosePresentationModel> = emptyList(),
    val patient: List<PatientPresentationModel> = emptyList()
) : Parcelable
