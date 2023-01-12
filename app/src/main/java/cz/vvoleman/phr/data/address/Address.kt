package cz.vvoleman.phr.data.address

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val city: String,
    val street: String,
    @ColumnInfo(name = "house_number") val houseNumber: String,
    @ColumnInfo(name = "zip_code") val zipCode: String
)
