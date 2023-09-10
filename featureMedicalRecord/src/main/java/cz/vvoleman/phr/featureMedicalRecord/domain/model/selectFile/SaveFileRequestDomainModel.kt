package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaveFileRequestDomainModel(
    val uri: String,
    val medicalRecordId: String
) : Parcelable
