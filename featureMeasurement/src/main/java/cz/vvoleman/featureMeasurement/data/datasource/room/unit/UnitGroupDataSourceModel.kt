package cz.vvoleman.featureMeasurement.data.datasource.room.unit

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import cz.vvoleman.featureMeasurement.data.datasource.room.converter.UnitConverter

@Entity(tableName = "unit_group")
@TypeConverters(UnitConverter::class)
data class UnitGroupDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val units: List<UnitDataSourceModel>,
    val defaultUnit: UnitDataSourceModel
)
