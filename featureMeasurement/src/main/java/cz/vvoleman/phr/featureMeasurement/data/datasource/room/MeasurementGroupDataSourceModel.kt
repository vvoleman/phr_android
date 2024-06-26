package cz.vvoleman.phr.featureMeasurement.data.datasource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurement_group")
data class MeasurementGroupDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    @ColumnInfo(name = "patient_id") val patientId: Int,
    @ColumnInfo(name = "problem_category_id") val problemCategoryId: Int? = null,
)
