package cz.vvoleman.featureMeasurement.data.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementGroupScheduleItemDao {

    @Transaction
    @Query("SELECT * FROM measurement_group_schedule_item WHERE measurement_group_id = :measurementGroupId")
    fun getByMeasurementGroup(measurementGroupId: Int): Flow<List<MeasurementGroupScheduleItemDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM measurement_group_schedule_item WHERE id = :id")
    fun getById(id: Int): Flow<MeasurementGroupScheduleItemDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: MeasurementGroupScheduleItemDataSourceModel): Long

    @Query("DELETE FROM measurement_group_schedule_item WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM measurement_group_schedule_item WHERE measurement_group_id = :measurementGroupId")
    fun deleteByMeasurementGroup(measurementGroupId: Int)

}
