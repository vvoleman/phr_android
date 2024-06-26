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
    fun getByMedicalWorker(medicalWorkerId: String): Flow<List<SpecificMedicalWorkerWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM specific_medical_worker WHERE medical_worker_id IN (:medicalWorkerIds)")
    fun getByMedicalWorker(medicalWorkerIds: List<String>): Flow<List<SpecificMedicalWorkerWithDetailsDataSourceModel>>

    @Transaction
    @Query(
        """
        SELECT mw.* FROM specific_medical_worker mw
        JOIN medical_service ms ON mw.medical_service_id = ms.id
        JOIN medical_facility mf ON ms.medical_facility_id = mf.id
        WHERE mf.id = :medicalFacilityId
    """
    )
    fun getByFacility(medicalFacilityId: String): Flow<List<SpecificMedicalWorkerWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM specific_medical_worker WHERE id = :id")
    fun getById(id: Int): Flow<SpecificMedicalWorkerWithDetailsDataSourceModel>

    @Transaction
    @Query(
        "SELECT * FROM specific_medical_worker " +
            "WHERE id IN (SELECT id FROM medical_worker WHERE patient_id = :patientId)"
    )
    fun getByPatient(patientId: String): Flow<List<SpecificMedicalWorkerWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM specific_medical_worker WHERE medical_service_id = :medicalServiceId")
    fun getByMedicalService(medicalServiceId: String): Flow<List<SpecificMedicalWorkerWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM specific_medical_worker WHERE medical_service_id IN (:medicalServiceIds)")
    fun getByMedicalService(
        medicalServiceIds: List<String>
    ): Flow<List<SpecificMedicalWorkerWithDetailsDataSourceModel>>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insert(specificMedicalWorker: SpecificMedicalWorkerDataSourceModel): Long

    @Delete
    suspend fun delete(specificMedicalWorker: SpecificMedicalWorkerDataSourceModel)

    @Query("DELETE FROM specific_medical_worker WHERE medical_worker_id = :medicalWorkerId")
    suspend fun deleteByMedicalWorker(medicalWorkerId: String)

    @Query("DELETE FROM specific_medical_worker WHERE medical_service_id = :medicalServiceId")
    suspend fun deleteByMedicalService(medicalServiceId: String)

    @Query("DELETE FROM specific_medical_worker WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM specific_medical_worker WHERE id IN (:ids)")
    suspend fun delete(ids: List<String>)

    @Query(
        "DELETE FROM specific_medical_worker " +
            "WHERE medical_worker_id = :workerId AND medical_service_id IN (:serviceIds)"
    )
    suspend fun delete(workerId: String, serviceIds: List<String>)
}
