package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class SelectedOptionsPresentationModel(
    val diagnoseId: String? = null,
    val visitDate: LocalDate? = null,
    val patientId: String? = null
) : Parcelable
