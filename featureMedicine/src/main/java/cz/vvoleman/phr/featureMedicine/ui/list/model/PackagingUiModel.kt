package cz.vvoleman.phr.featureMedicine.ui.list.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackagingUiModel(
    val form: ProductFormUiModel,
    val packaging: String
) : Parcelable
