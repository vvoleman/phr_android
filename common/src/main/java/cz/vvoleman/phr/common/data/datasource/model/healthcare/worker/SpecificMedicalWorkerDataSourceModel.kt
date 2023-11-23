package cz.vvoleman.phr.common.data.datasource.model.healthcare.worker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "specific_medical_worker")
data class SpecificMedicalWorkerDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "medical_worker_id") val medicalWorkerId: Int,
    @ColumnInfo(name = "medical_service_id") val medicalServiceId: Int,
    val email: String? = null,
    val telephone: String? = null,
)
