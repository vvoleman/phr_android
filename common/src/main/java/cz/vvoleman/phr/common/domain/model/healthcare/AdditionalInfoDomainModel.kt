package cz.vvoleman.phr.common.domain.model.healthcare

import android.os.Parcelable

data class AdditionalInfoDomainModel<T : Parcelable>(
    val icon: Int? = null,
    val text: String,
    val onClick: ((T) -> Unit)? = null
)
