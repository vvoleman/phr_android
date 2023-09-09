package cz.vvoleman.phr.feature_medicine.data.datasource.room.schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "schedule_item")
data class ScheduleItemDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val day_of_week: DayOfWeek,
    val time: LocalTime,
    val scheduled_at: LocalDateTime,
    val ending_at: LocalDateTime? = null,
    val quantity: String,
    val schedule_id: Int,
    val description: String? = null
)
