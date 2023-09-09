package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaveFileRequestDomainModel(
    val uri: String,
    val medicalRecordId: String
) : Parcelable
