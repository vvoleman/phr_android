package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalRecordDao {

    @Query("SELECT * FROM medical_record ORDER BY :sortBy DESC")
    fun getAll(sortBy: String): Flow<List<MedicalRecordWithDetails>>

    @Query("SELECT * FROM medical_record WHERE id = :id")
    fun getById(id: String): Flow<MedicalRecordWithDetails>

    @Query("SELECT * FROM medical_record WHERE patient_id = :patientId ORDER BY :sortBy DESC")
    fun getByPatientId(patientId: String, sortBy: String): Flow<List<MedicalRecordWithDetails>>

    @Query(
        "SELECT * FROM medical_record WHERE patient_id = :patientId AND medical_worker_id IN (:workerIds) AND problem_category_id IN (:categoryIds) ORDER BY :sortBy DESC"
    )
    fun filter(
        patientId: String,
        sortBy: String,
        workerIds: List<String>,
        categoryIds: List<String>
    ): Flow<List<MedicalRecordWithDetails>>

    @Query(
        "SELECT * FROM medical_record WHERE patient_id = :patientId AND problem_category_id IN (:categoryIds) ORDER BY :sortBy DESC"
    )
    fun filterInCategory(
        patientId: String,
        sortBy: String,
        categoryIds: List<String>
    ): Flow<List<MedicalRecordWithDetails>>

    @Query(
        "SELECT * FROM medical_record WHERE patient_id = :patientId AND medical_worker_id IN (:workerIds) ORDER BY :sortBy DESC"
    )
    fun filterInWorker(
        patientId: String,
        sortBy: String,
        workerIds: List<String>
    ): Flow<List<MedicalRecordWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicalRecord: MedicalRecordDataSourceModel): Long

    @Update
    suspend fun update(medicalRecord: MedicalRecordDataSourceModel)

    @Delete
    suspend fun delete(medicalRecord: MedicalRecordDataSourceModel)

    @Query("DELETE FROM medical_record WHERE patient_id = :patientId")
    suspend fun deleteByPatient(patientId: String)
}
