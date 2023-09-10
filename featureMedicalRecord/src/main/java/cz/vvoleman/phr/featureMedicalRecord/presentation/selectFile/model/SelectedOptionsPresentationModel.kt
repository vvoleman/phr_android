package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class SelectedOptionsPresentationModel(
    val diagnoseId: String? = null,
    val visitDate: LocalDate? = null,
    val patientId: String? = null
) : Parcelable
