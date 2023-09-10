package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "medical_record_asset")
data class MedicalRecordAssetDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "medical_record_id") val medicalRecordId: Int,
    val uri: String,
    val createdAt: LocalDate
)
