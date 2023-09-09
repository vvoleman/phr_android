package cz.vvoleman.phr.data.room.medical_record.asset

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cz.vvoleman.phr.data.core.medical_record.AssetType
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalRecordAssetDao {

    @Query("SELECT * FROM medical_record_asset WHERE medical_record_id = :medicalRecordId")
    fun getAllForRecord(medicalRecordId: Int): Flow<List<MedicalRecordAssetEntity>>

    @Query("SELECT * FROM medical_record_asset WHERE medical_record_id = :medicalRecordId AND type = :type")
    fun getAllForRecordAndType(medicalRecordId: Int, type: AssetType): Flow<List<MedicalRecordAssetEntity>>

    @Query("SELECT * FROM medical_record_asset WHERE id = :id")
    fun getById(id: Int): Flow<MedicalRecordAssetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asset: MedicalRecordAssetEntity)

    @Update
    suspend fun update(asset: MedicalRecordAssetEntity)

    @Delete
    suspend fun delete(asset: MedicalRecordAssetEntity)

    @Query("DELETE FROM medical_record_asset WHERE medical_record_id = :medicalRecordId")
    suspend fun deleteAllForRecord(medicalRecordId: Int)
}
