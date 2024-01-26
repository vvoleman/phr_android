package cz.vvoleman.phr.featureMeasurement.data.datasource.room.field

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numeric_field")
data class NumericFieldDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    @ColumnInfo(name = "measurement_group_id") val measurementGroupId: Int,
    val unitGroupId: Int,
    @ColumnInfo(name="minimal_values") val minimalValue: Number? = null,
    @ColumnInfo(name="maximal_values") val maximalValue: Number? = null,
)
