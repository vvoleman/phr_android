package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class PatientPresentationModel(
    val id: String,
    val name: String,
    val birthDate: LocalDate? = null
) : Parcelable
