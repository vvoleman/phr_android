package cz.vvoleman.phr.feature_medicine.data.datasource.room.schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "medicine_schedule")
data class MedicineScheduleDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patient_id: Int,
    val medicine_id: Int,
    val createdAt: LocalDateTime
)
