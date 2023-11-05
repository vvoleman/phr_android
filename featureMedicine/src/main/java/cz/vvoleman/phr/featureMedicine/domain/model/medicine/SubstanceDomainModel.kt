package cz.vvoleman.phr.featureMedicine.domain.model.medicine

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubstanceDomainModel(
    val id: String,
    val name: String
) : Parcelable
