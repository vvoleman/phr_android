package cz.vvoleman.phr.data.room.address

import androidx.room.Entity
import cz.vvoleman.phr.data.core.Address

@Entity(tableName = "address")
data class AddressEntity(
    val city: String,
    val street: String,
    val house_number: String,
    val zip_code: String
) {

    companion object {
        fun from(address: Address): AddressEntity {
            return AddressEntity(
                city = address.city,
                street = address.street,
                house_number = address.houseNumber,
                zip_code = address.zipCode
            )
        }
    }

    fun toAddress(): Address {
        return Address(
            city = city,
            street = street,
            houseNumber = house_number,
            zipCode = zip_code
        )
    }
}
