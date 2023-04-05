package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packaging")
data class PackagingDataSourceModel(
    @PrimaryKey(autoGenerate = true) val packagingId: Int = 1,
    @Embedded val productForm: ProductFormDataSourceModel,
    val medicine_id: String,
    val packaging: String,
)
