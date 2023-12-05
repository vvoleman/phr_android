package cz.vvoleman.phr.common.domain.facade

object AddressFacade {
    fun formatAddress(street: String, houseNumber: String, zipCode: String, city: String): String {
        return when {
            street.isEmpty() -> {
                "$city $houseNumber, $zipCode $city"
            }
            houseNumber.isEmpty() -> {
                "$street, $zipCode $city"
            }
            else -> {
                "$street $houseNumber, $zipCode $city"
            }
        }
    }
}
