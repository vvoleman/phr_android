package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "medicine_schedule")
data class MedicineScheduleDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "patient_id") val patientId: Int,
    @ColumnInfo(name = "medicine_id") val medicineId: String,
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "is_alarm_enabled") val isAlarmEnabled: Boolean = true,
)
