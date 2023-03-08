package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.asset

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "medical_record_asset")
data class MedicalRecordAssetDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val medical_record_id: Int,
    val type: String,
    val uri: String,
    val createdAt: LocalDate
)