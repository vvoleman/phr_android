package cz.vvoleman.phr.common.data.datasource.model.healthcare.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalServiceDao {

    @Query("SELECT * FROM medical_service")
    fun getAll(): Flow<List<MedicalServiceDataSourceModel>>

    @Query("SELECT * FROM medical_service WHERE id = :id")
    fun getById(id: String): Flow<MedicalServiceDataSourceModel>

    @Query("SELECT * FROM medical_service WHERE medical_facility_id = :facilityId")
    fun getByFacilityId(facilityId: Int): Flow<List<MedicalServiceDataSourceModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicalService: MedicalServiceDataSourceModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medicalServices: List<MedicalServiceDataSourceModel>)

    @Query("DELETE FROM medical_service WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM medical_service WHERE medical_facility_id = :facilityId")
    suspend fun deleteByFacilityId(facilityId: Int)
}
