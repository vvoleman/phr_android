package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine")
data class MedicineDataSourceModel(
    @PrimaryKey val id: String,
    val name: String,
    val country: String,
    val substances: List<SubstanceAmountDataSourceModel>,
    @Embedded val packaging: PackagingDataSourceModel
)
