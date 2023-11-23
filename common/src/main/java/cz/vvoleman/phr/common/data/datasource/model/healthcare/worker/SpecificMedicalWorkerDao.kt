package cz.vvoleman.phr.common.data.datasource.model.healthcare.worker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecificMedicalWorkerDao {

    @Transaction
    @Query("SELECT * FROM specific_medical_worker WHERE medical_worker_id = :medicalWorkerId")
    fun getByMedicalWorker(medicalWorkerId: Int): Flow<List<SpecificMedicalWorkerWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM specific_medical_worker WHERE id = :id")
    fun getById(id: Int): Flow<SpecificMedicalWorkerWithDetailsDataSourceModel>

    @Transaction
    @Query("SELECT * FROM specific_medical_worker WHERE medical_service_id = :medicalServiceId")
    fun getByMedicalService(medicalServiceId: Int): Flow<List<SpecificMedicalWorkerWithDetailsDataSourceModel>>

    @Insert
    suspend fun insert(specificMedicalWorker: SpecificMedicalWorkerDataSourceModel): Long

    @Delete
    suspend fun delete(specificMedicalWorker: SpecificMedicalWorkerDataSourceModel)

    @Query("DELETE FROM specific_medical_worker WHERE medical_worker_id = :medicalWorkerId")
    suspend fun deleteByMedicalWorker(medicalWorkerId: Int)

    @Query("DELETE FROM specific_medical_worker WHERE medical_service_id = :medicalServiceId")
    suspend fun deleteByMedicalService(medicalServiceId: Int)

    @Query("DELETE FROM specific_medical_worker WHERE id = :id")
    suspend fun delete(id: Int)
}
