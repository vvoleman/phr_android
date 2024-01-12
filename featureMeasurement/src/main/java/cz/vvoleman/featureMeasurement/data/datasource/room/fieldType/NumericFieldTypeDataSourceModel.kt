package cz.vvoleman.featureMeasurement.data.datasource.room.fieldType

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numeric_field_type")
data class NumericFieldTypeDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    @ColumnInfo(name = "measurement_group_id") val measurementGroupId: Int,
    val unitGroupId: Int? = null,
    val defaultUnitCode: String? = null,
)
