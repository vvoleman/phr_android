package cz.vvoleman.phr.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class PatientDomainModel(
    val id: String,
    val name: String,
    val birthDate: LocalDate? = null,
): Parcelable
