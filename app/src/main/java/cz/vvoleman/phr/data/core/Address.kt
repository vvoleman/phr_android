package cz.vvoleman.phr.data.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val city: String,
    val street: String,
    val houseNumber: String,
    val zipCode: String
) : Parcelable {

    override fun toString(): String {
        return "$street $houseNumber, $zipCode $city"
    }

}
