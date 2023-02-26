package cz.vvoleman.phr.data.medical_records

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalRecordDao {

    @Transaction
    @Query("SELECT * FROM medical_records")
    fun getAllMedicalRecords(): Flow<List<MedicalRecordWithDetails>>

    @Transaction
    @Query("SELECT * FROM medical_records WHERE recordId = :id")
    fun getMedicalRecordById(id: Int): Flow<MedicalRecordWithDetails>

    @Transaction
    @Query("SELECT * FROM medical_records WHERE facilityId = :facilityId")
    fun getMedicalRecordByFacility(facilityId: Int): Flow<MedicalRecordWithDetails>

    @Transaction
    @Query("SELECT * FROM medical_records WHERE patientId = :patientId")
    fun getMedicalRecordByPatient(patientId: Int): Flow<List<MedicalRecordWithDetails>>

    @Insert
    suspend fun insertMedicalRecord(medicalRecord: MedicalRecord)

    @Update
    suspend fun updateMedicalRecord(medicalRecord: MedicalRecord)

    @Delete
    suspend fun deleteMedicalRecord(medicalRecord: MedicalRecord)

}