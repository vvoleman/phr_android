package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalWorkerDao {

    @Query("SELECT * FROM medical_worker")
    fun getAll(): Flow<List<MedicalWorkerDataSourceModel>>

    @Query("SELECT * FROM medical_worker WHERE id = :id")
    fun getById(id: Int): Flow<MedicalWorkerDataSourceModel>

    @Query("SELECT * FROM medical_worker WHERE patientId = :patientId")
    fun getAll(patientId: String): Flow<List<MedicalWorkerDataSourceModel>>

    @Query(
        "SELECT * FROM medical_worker WHERE id IN (SELECT medical_worker_id FROM medical_record WHERE patient_id = :patientId)"
    )
    fun getUsedByPatientId(patientId: String): Flow<List<MedicalWorkerDataSourceModel>>

    @Query("SELECT * FROM medical_worker WHERE name = :name")
    fun getByName(name: String): Flow<MedicalWorkerDataSourceModel>

    @Insert
    suspend fun insert(medicalWorker: MedicalWorkerDataSourceModel)

    @Update
    suspend fun update(medicalWorker: MedicalWorkerDataSourceModel)

    @Delete
    suspend fun delete(medicalWorker: MedicalWorkerDataSourceModel)

    @Query("DELETE FROM medical_worker WHERE patientId = :patientId")
    suspend fun deleteByPatient(patientId: String)
}
