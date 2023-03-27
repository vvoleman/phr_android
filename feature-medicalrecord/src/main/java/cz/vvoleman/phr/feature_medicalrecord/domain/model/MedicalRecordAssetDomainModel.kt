package cz.vvoleman.phr.feature_medicalrecord.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MedicalRecordAssetDomainModel(
    val id: String,
    val url: String,
    val medicalRecordId: String,
    val createdAt: LocalDate = LocalDate.now()
) : Parcelable
