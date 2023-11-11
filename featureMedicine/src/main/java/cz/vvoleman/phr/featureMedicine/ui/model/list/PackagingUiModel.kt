package cz.vvoleman.phr.featureMedicine.ui.model.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackagingUiModel(
    val form: ProductFormUiModel,
    val packaging: String
) : Parcelable
