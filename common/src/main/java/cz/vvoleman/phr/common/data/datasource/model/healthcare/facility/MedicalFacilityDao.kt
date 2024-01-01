package cz.vvoleman.phr.common.data.datasource.model.healthcare.facility

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalFacilityDao {

    @Query("SELECT * FROM medical_facility")
    fun getAll(): Flow<List<MedicalFacilityWithDetailsDataSourceModel>>

    @Query("""
    SELECT mf.* FROM medical_facility mf
    JOIN medical_service ms ON mf.id = ms.medical_facility_id
    JOIN specific_medical_worker smw ON ms.id = smw.medical_service_id
    JOIN medical_worker mw ON smw.medical_worker_id = mw.id
    WHERE mw.patient_id = :patientId
    """)
    fun getByPatient(patientId: String): Flow<List<MedicalFacilityWithDetailsDataSourceModel>>

    @Query("SELECT * FROM medical_facility WHERE id = :id")
    fun getById(id: Int): Flow<MedicalFacilityWithDetailsDataSourceModel>

    @Query("SELECT * FROM medical_facility WHERE full_name = :name")
    fun getByName(name: String): Flow<MedicalFacilityWithDetailsDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicalFacility: MedicalFacilityDataSourceModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medicalFacilities: List<MedicalFacilityDataSourceModel>)

    @Query("DELETE FROM medical_facility WHERE id = :id")
    suspend fun delete(id: Int)

    @Delete
    suspend fun delete(medicalFacility: MedicalFacilityDataSourceModel)
}
