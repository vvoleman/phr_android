package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.asset

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalRecordAssetDao {

    @Query("SELECT * FROM medical_record_asset WHERE medical_record_id = :medicalRecordId")
    fun getAllForRecord(medicalRecordId: Int): Flow<List<MedicalRecordAssetDataSourceModel>>

    @Query("SELECT * FROM medical_record_asset WHERE medical_record_id = :medicalRecordId AND type = :type")
    fun getAllForRecordAndType(medicalRecordId: Int, type: String): Flow<List<MedicalRecordAssetDataSourceModel>>

    @Query("SELECT * FROM medical_record_asset WHERE id = :id")
    fun getById(id: Int): Flow<MedicalRecordAssetDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asset: MedicalRecordAssetDataSourceModel)

    @Update
    suspend fun update(asset: MedicalRecordAssetDataSourceModel)

    @Delete
    suspend fun delete(asset: MedicalRecordAssetDataSourceModel)

    @Query("DELETE FROM medical_record_asset WHERE medical_record_id = :medicalRecordId")
    suspend fun deleteAllForRecord(medicalRecordId: Int)


}