package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room

import androidx.room.*
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerWithDetailsDataSourceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalRecordDao {

    @Transaction
    @Query("SELECT * FROM medical_record ORDER BY :sortBy DESC")
    fun getAll(sortBy: String): Flow<List<MedicalRecordWithDetails>>

    @Transaction
    @Query("SELECT * FROM medical_record WHERE id = :id")
    fun getById(id: String): Flow<MedicalRecordWithDetails>

    @Transaction
    @Query("SELECT * FROM medical_record WHERE patient_id = :patientId ORDER BY :sortBy DESC")
    fun getByPatientId(patientId: String, sortBy: String): Flow<List<MedicalRecordWithDetails>>

    @Transaction
    @Query(
        "SELECT * FROM medical_record " +
            "WHERE patient_id = :patientId AND specific_medical_worker_id IN (:workerIds) " +
            "AND problem_category_id IN (:categoryIds) ORDER BY :sortBy DESC"
    )
    fun filter(
        patientId: String,
        sortBy: String,
        workerIds: List<String>,
        categoryIds: List<String>
    ): Flow<List<MedicalRecordWithDetails>>

    @Transaction
    @Query(
        "SELECT * FROM medical_record " +
            "WHERE patient_id = :patientId AND problem_category_id IN (:categoryIds) ORDER BY :sortBy DESC"
    )
    fun filterInCategory(
        patientId: String,
        sortBy: String,
        categoryIds: List<String>
    ): Flow<List<MedicalRecordWithDetails>>

    @Transaction
    @Query(
        "SELECT * FROM medical_record " +
            "WHERE patient_id = :patientId AND specific_medical_worker_id IN (:workerIds) ORDER BY :sortBy DESC"
    )
    fun filterInWorker(
        patientId: String,
        sortBy: String,
        workerIds: List<String>
    ): Flow<List<MedicalRecordWithDetails>>

    @Query(
        "SELECT * FROM specific_medical_worker " +
            "WHERE id IN (SELECT specific_medical_worker_id FROM medical_record WHERE patient_id = :patientId)"
    )
    fun getUsedWorkersByPatientId(patientId: String): Flow<List<SpecificMedicalWorkerWithDetailsDataSourceModel>>

    @Transaction
    @Query(
        "SELECT * FROM medical_record WHERE specific_medical_worker_id IN " +
            "(SELECT id FROM specific_medical_worker WHERE medical_worker_id = :medicalWorkerId)"
    )
    fun getByMedicalWorkerId(medicalWorkerId: String): Flow<List<MedicalRecordWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicalRecord: MedicalRecordDataSourceModel): Long

    @Update
    suspend fun update(medicalRecord: MedicalRecordDataSourceModel)

    @Delete
    suspend fun delete(medicalRecord: MedicalRecordDataSourceModel)

    @Query("DELETE FROM medical_record WHERE patient_id = :patientId")
    suspend fun deleteByPatient(patientId: String)
}
