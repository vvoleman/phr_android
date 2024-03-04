package cz.vvoleman.phr.featureMeasurement.data.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementGroupDao {

    @Transaction
    @Query("SELECT * FROM measurement_group")
    fun getAll(): Flow<List<MeasurementGroupWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM measurement_group WHERE patient_id = :patientId")
    fun getByPatient(patientId: Int): Flow<List<MeasurementGroupWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM measurement_group WHERE problem_category_id = :problemCategoryId")
    fun getByProblemCategory(problemCategoryId: Int): Flow<List<MeasurementGroupWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM measurement_group WHERE id = :id")
    fun getById(id: Int): Flow<MeasurementGroupWithDetailsDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurementGroup: MeasurementGroupDataSourceModel): Long

    @Query("DELETE FROM measurement_group WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM measurement_group WHERE patient_id = :patientId")
    suspend fun deleteByPatientId(patientId: String)

}
