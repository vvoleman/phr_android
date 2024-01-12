package cz.vvoleman.featureMeasurement.data.datasource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "measurement_group_schedule_item")
data class MeasurementGroupScheduleItemDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    @ColumnInfo(name = "scheduled_at") val scheduledAt: LocalDateTime,
    @ColumnInfo(name = "measurement_group_id") val measurementGroupId: Int,
    val description: String? = null
)