package cz.vvoleman.phr.feature_medicine.data.datasource.room.medicine

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "substance_amount")
data class SubstanceAmountDataSourceModel(
    @PrimaryKey(autoGenerate = true) val substanceAmountId: Int = 1,
    @Embedded val substance: SubstanceDataSourceModel,
    val medicine_id: String,
    val amount: String,
)
