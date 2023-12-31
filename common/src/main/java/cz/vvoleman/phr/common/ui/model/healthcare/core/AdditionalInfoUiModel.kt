package cz.vvoleman.phr.common.ui.model.healthcare.core

import android.os.Parcelable

data class AdditionalInfoUiModel<T: Parcelable>(
    val icon: Int? = null,
    val text: String,
    val onClick: ((T) -> Unit)? = null
)
