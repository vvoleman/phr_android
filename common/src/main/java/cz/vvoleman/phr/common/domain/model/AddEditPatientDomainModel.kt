package cz.vvoleman.phr.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class AddEditPatientDomainModel(
    val id: String? = null,
    val name: String,
    val birthDate: LocalDate? = null
) : Parcelable
