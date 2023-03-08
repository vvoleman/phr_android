package cz.vvoleman.phr.common.data.datasource.model

import androidx.room.Entity

@Entity(tableName = "address")
data class AddressDataSourceModel(
    val city: String,
    val street: String,
    val house_number: String,
    val zip_code: String
)