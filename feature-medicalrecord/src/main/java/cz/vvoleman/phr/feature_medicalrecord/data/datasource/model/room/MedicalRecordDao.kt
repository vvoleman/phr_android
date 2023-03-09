package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room

import androidx.room.*
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalRecordDao {

    @Query("SELECT * FROM medical_record ORDER BY :sortBy DESC")
    fun getAll(sortBy: String): Flow<List<MedicalRecordWithDetails>>

    @Query("SELECT * FROM medical_record WHERE id = :id")
    fun getById(id: Int): Flow<MedicalRecordWithDetails>

    @Query("SELECT * FROM medical_record WHERE patient_id = :patientId")
    fun getByPatientId(patientId: Int): Flow<List<MedicalRecordWithDetails>>

    @Query("SELECT * FROM medical_record WHERE medical_worker_id IN (:workerIds) AND problem_category_id IN (:categoryIds) ORDER BY :sortBy DESC")
    fun filter(
        sortBy: String,
        workerIds: List<String>,
        categoryIds: List<String>
    ): Flow<List<MedicalRecordWithDetails>>

    @Query("SELECT * FROM medical_record WHERE problem_category_id IN (:categoryIds) ORDER BY :sortBy DESC")
    fun filterInCategory(
        sortBy: String,
        categoryIds: List<String>
    ): Flow<List<MedicalRecordWithDetails>>

    @Query("SELECT * FROM medical_record WHERE medical_worker_id IN (:workerIds) ORDER BY :sortBy DESC")
    fun filterInWorker(
        sortBy: String,
        workerIds: List<String>
    ): Flow<List<MedicalRecordWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicalRecord: MedicalRecordDataSourceModel)

    @Update
    suspend fun update(medicalRecord: MedicalRecordDataSourceModel)

    @Delete
    suspend fun delete(medicalRecord: MedicalRecordDataSourceModel)

}