package cz.vvoleman.phr.data.room.medical_record.asset

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.core.medical_record.AssetType
import cz.vvoleman.phr.data.core.medical_record.MedicalRecordAsset
import java.time.LocalDate

@Entity(tableName = "medical_record_asset")
data class MedicalRecordAssetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val medical_record_id: Int,
    val type: AssetType,
    val uri: String,
    val createdAt: LocalDate
) {

    companion object {
        fun from(asset: MedicalRecordAsset, recordId: Int) {
            MedicalRecordAssetEntity(
                id = asset.id ?: 0,
                medical_record_id = recordId,
                type = asset.type,
                uri = asset.uri,
                createdAt = asset.createdAt
            )
        }
    }

    fun toMedicalRecordAsset(): MedicalRecordAsset {
        return MedicalRecordAsset(
            id = id,
            type = type,
            uri = uri,
            createdAt = createdAt
        )
    }
}
