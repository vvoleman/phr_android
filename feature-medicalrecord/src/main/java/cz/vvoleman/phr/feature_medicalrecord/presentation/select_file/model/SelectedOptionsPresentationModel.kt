package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import android.net.Uri
import android.os.Parcelable
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.DiagnosePresentationModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.PatientPresentationModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class SelectedOptionsPresentationModel(
    val diagnoseId: String? = null,
    val visitDate: LocalDate? = null,
    val patientId: String? = null,
) : Parcelable
