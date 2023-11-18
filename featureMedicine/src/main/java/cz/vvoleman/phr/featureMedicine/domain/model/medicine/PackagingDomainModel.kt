package cz.vvoleman.phr.featureMedicine.domain.model.medicine

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackagingDomainModel(
    val form: ProductFormDomainModel,
    val packaging: String
) : Parcelable
