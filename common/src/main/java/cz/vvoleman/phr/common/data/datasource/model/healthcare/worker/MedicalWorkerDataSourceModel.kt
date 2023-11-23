package cz.vvoleman.phr.common.data.datasource.model.healthcare.worker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medical_worker")
data class MedicalWorkerDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    @ColumnInfo(name = "patient_id") val patientId: Int,
)
