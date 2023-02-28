package cz.vvoleman.phr.data.room.medical_record

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalRecordDao {

    @Query("SELECT * FROM medical_record")
    fun getAll(): Flow<List<MedicalRecordWithDetails>>

    @Query("SELECT * FROM medical_record WHERE id = :id")
    fun getById(id: Int): Flow<MedicalRecordWithDetails>

    @Query("SELECT * FROM medical_record WHERE patient_id = :patientId")
    fun getByPatientId(patientId: Int): Flow<List<MedicalRecordWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicalRecord: MedicalRecordEntity)

    @Update
    suspend fun update(medicalRecord: MedicalRecordEntity)

    @Delete
    suspend fun delete(medicalRecord: MedicalRecordEntity)

}