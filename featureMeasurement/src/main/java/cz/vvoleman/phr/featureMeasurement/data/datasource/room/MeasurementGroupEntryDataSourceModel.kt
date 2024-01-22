package cz.vvoleman.phr.featureMeasurement.data.datasource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.converter.ScheduleItemValueConverter
import java.time.LocalDateTime

@Entity(tableName = "measurement_group_entry")
@TypeConverters(ScheduleItemValueConverter::class)
data class MeasurementGroupEntryDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "measurement_group_id") val measurementGroupId: Int,
    val values: Map<String, String>,
    @ColumnInfo(name = "schedule_item_id") val scheduleItemId: Int? = null
)
