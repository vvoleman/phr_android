package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class PackagingDataSourceModel(
    val product_form_id: String,
    val packaging: String,
)
