package cz.vvoleman.phr.featureMedicalRecord.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiagnoseGroupDomainModel(
    val id: String,
    val name: String
) : Parcelable
