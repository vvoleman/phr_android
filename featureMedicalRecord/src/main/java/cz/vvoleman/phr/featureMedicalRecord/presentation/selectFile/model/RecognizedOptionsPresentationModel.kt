package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model

import android.os.Parcelable
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.DiagnosePresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.PatientPresentationModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class RecognizedOptionsPresentationModel(
    val visitDate: List<LocalDate> = emptyList(),
    val diagnose: List<DiagnosePresentationModel> = emptyList(),
    val patient: List<PatientPresentationModel> = emptyList()
) : Parcelable
