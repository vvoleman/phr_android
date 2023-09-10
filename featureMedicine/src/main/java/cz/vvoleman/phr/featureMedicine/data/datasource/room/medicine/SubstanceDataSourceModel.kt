package cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "substance")
data class SubstanceDataSourceModel(
    @PrimaryKey val id: String,
    val name: String
)
