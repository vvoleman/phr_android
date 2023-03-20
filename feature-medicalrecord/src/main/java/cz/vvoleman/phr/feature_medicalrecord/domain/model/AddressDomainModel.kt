package cz.vvoleman.phr.feature_medicalrecord.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressDomainModel(
    val city: String,
    val street: String,
    val houseNumber: String,
    val zipCode: String
) : Parcelable
