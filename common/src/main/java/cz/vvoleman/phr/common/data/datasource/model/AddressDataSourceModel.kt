package cz.vvoleman.phr.common.data.datasource.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "address")
data class AddressDataSourceModel(
    val city: String,
    val street: String,
    @ColumnInfo(name = "house_number") val houseNumber: String,
    @ColumnInfo(name = "zip_code") val zipCode: String
)
