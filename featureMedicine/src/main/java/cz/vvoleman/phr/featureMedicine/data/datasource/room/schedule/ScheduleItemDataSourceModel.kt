package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "schedule_item")
data class ScheduleItemDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    @ColumnInfo(name = "scheduled_at") val scheduledAt: LocalDateTime,
    @ColumnInfo(name = "ending_at") val endingAt: LocalDateTime? = null,
    val quantity: String,
    @ColumnInfo(name = "schedule_id") val scheduleId: Int,
    val description: String? = null
)
