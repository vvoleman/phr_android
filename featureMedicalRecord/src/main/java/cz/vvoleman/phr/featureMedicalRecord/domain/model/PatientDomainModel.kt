package cz.vvoleman.phr.featureMedicalRecord.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class PatientDomainModel(
    val id: String,
    val name: String,
    val birthDate: LocalDate?
) : Parcelable
