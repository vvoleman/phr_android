package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset

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

    @Query("SELECT * FROM medical_record_asset WHERE id = :id")
    fun getById(id: Int): Flow<MedicalRecordAssetDataSourceModel>

    @Query(
        "SELECT * FROM medical_record_asset " +
            "WHERE medical_record_id IN (SELECT id FROM medical_record WHERE patient_id = :patientId)"
    )
    fun getByPatient(patientId: String): Flow<List<MedicalRecordAssetDataSourceModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asset: MedicalRecordAssetDataSourceModel): Long

    @Update
    suspend fun update(asset: MedicalRecordAssetDataSourceModel)

    @Delete
    suspend fun delete(asset: MedicalRecordAssetDataSourceModel)

    @Query("DELETE FROM medical_record_asset WHERE medical_record_id = :medicalRecordId")
    suspend fun deleteAllForRecord(medicalRecordId: String)

    @Query(
        "DELETE FROM medical_record_asset " +
            "WHERE medical_record_id IN (SELECT id FROM medical_record WHERE patient_id = :patientId)"
    )
    suspend fun deleteByPatient(patientId: String)
}
