package cz.vvoleman.phr.common.presentation.model.patient

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class PatientPresentationModel(
    val id: String,
    val name: String,
    val birthDate: LocalDate? = null
) : Parcelable
