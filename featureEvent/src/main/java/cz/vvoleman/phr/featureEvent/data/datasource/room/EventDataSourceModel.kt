package cz.vvoleman.phr.featureEvent.data.datasource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime

@Entity(tableName = "event")
@TypeConverters()
data class EventDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    @ColumnInfo(name = "start_at") val startAt: LocalDateTime,
    @ColumnInfo(name = "end_at") val endAt: LocalDateTime? = null,
    @ColumnInfo(name = "patient_id") val patientId: Int,
    @ColumnInfo(name = "specific_medical_worker_id") val specificMedicalWorkerId: Int? = null,
    val description: String? = null,
    val reminders: List<Long>
)
