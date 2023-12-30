package cz.vvoleman.phr.common.data.datasource.model.healthcare.worker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalWorkerDao {

    @Query("SELECT * FROM medical_worker WHERE patient_id = :patientId")
    fun getAll(patientId: String): Flow<List<MedicalWorkerDataSourceModel>>

    @Query("SELECT * FROM medical_worker WHERE patient_id = :patientId")
    fun getWithServices(patientId: String): Flow<List<MedicalWorkerDataSourceModel>>

    @Query("SELECT * FROM medical_worker WHERE id = :id")
    fun getById(id: Int): Flow<MedicalWorkerDataSourceModel>

    @Query("SELECT * FROM medical_worker WHERE name = :name")
    fun getByName(name: String): Flow<MedicalWorkerDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicalWorker: MedicalWorkerDataSourceModel): Long

    @Query("DELETE FROM medical_worker WHERE patient_id = :patientId")
    suspend fun deleteByPatient(patientId: String)

    @Query("DELETE FROM medical_worker WHERE id = :id")
    suspend fun delete(id: Int)

    @Delete
    suspend fun delete(medicalWorker: MedicalWorkerDataSourceModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicalWorker: SpecificMedicalWorkerDataSourceModel)

    @Delete
    suspend fun delete(medicalWorker: SpecificMedicalWorkerDataSourceModel)
}
