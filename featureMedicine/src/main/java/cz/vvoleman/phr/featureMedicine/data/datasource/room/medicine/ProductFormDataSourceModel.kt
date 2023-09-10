package cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_form")
data class ProductFormDataSourceModel(
    @PrimaryKey val id: String,
    val name: String
)
