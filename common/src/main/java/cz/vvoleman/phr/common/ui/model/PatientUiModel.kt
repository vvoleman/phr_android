package cz.vvoleman.phr.common.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class PatientUiModel(
    val id: String,
    val name: String,
    val birthDate: LocalDate? = null,
    val isSelected: Boolean = false
): Parcelable
