package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.ScheduleItemDataSourceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleItemDao {

    @Query("SELECT * FROM schedule_item WHERE schedule_id = :scheduleId")
    fun getAll(scheduleId: String): Flow<List<ScheduleItemDataSourceModel>>

    @Query("SELECT * FROM schedule_item WHERE schedule_id IN (SELECT id FROM medicine_schedule WHERE patient_id = :patientId)")
    fun getAllByPatient(patientId: String): Flow<List<ScheduleItemDataSourceModel>>

    @Query("SELECT * FROM schedule_item WHERE id = :id")
    fun getById(id: String): Flow<ScheduleItemDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scheduleItem: ScheduleItemDataSourceModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scheduleItems: List<ScheduleItemDataSourceModel>): List<Long>

    @Delete
    suspend fun delete(scheduleItem: ScheduleItemDataSourceModel)
}
