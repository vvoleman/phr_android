package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.common.data.datasource.model.AddressDataSourceModel

@Entity(tableName = "medical_worker")
data class MedicalWorkerDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val patientId: Int,
    val email: String?,
    val phone: String?,
    @Embedded val address: AddressDataSourceModel,
)
