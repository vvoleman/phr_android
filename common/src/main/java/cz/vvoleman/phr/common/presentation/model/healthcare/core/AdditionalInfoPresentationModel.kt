package cz.vvoleman.phr.common.presentation.model.healthcare.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdditionalInfoPresentationModel<T: Parcelable> (
    val icon: Int? = null,
    val text: String,
    val onClick: ((T) -> Unit)? = null
): Parcelable
