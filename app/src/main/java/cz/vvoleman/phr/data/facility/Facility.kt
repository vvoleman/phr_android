package cz.vvoleman.phr.data.facility

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.room.address.AddressEntity

@Entity(tableName = "facilities")
data class Facility(
    @PrimaryKey val id: Int,
    @Embedded val address: AddressEntity,
    val name: String,
    val phone: String?,
    val email: String?,
    val fax: String?,
    val website: String?
)
