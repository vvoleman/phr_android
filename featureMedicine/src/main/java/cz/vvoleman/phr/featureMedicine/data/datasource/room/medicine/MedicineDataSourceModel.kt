package cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import cz.vvoleman.phr.featureMedicine.data.datasource.room.ListSubstanceAmountDataSourceConverter
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.ListSubstanceAmountDataSourceModel

@Entity(tableName = "medicine")
@TypeConverters(ListSubstanceAmountDataSourceConverter::class)
data class MedicineDataSourceModel(
    @PrimaryKey val id: String,
    val name: String,
    val country: String,
    val substances: ListSubstanceAmountDataSourceModel,
    @Embedded val packaging: PackagingDataSourceModel
)
