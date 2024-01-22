package cz.vvoleman.phr.featureMeasurement.data.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementGroupEntryDao {

    @Transaction
    @Query("SELECT * FROM measurement_group_entry WHERE measurement_group_id = :measurementGroupId")
    fun getByMeasurementGroupId(measurementGroupId: Int): Flow<List<MeasurementGroupEntryDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM measurement_group_entry WHERE id = :id")
    fun getById(id: String): Flow<MeasurementGroupEntryDataSourceModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: MeasurementGroupEntryDataSourceModel): Long

    @Query("DELETE FROM measurement_group_entry WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM measurement_group_entry WHERE measurement_group_id = :measurementGroupId")
    suspend fun deleteByMeasurementGroupId(measurementGroupId: String)

}
