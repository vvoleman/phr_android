package cz.vvoleman.phr.ui.medical_records.add_edit.recognizer.dialog

import android.os.Parcelable
import cz.vvoleman.phr.data.core.Patient
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class SelectedOptions(
    val visitDate: LocalDate?,
    val patient: Patient?,
    val diagnose: String?
) : Parcelable {}
