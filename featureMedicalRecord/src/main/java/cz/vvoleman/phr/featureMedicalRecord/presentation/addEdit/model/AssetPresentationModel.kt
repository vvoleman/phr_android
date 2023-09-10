package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class AssetPresentationModel(
    val id: String? = null,
    val createdAt: LocalDate = LocalDate.now(),
    val uri: String
) : Parcelable
