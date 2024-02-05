package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.MedicineScheduleDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.ScheduleWithDetailsDataSourceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineScheduleDao {

    @Transaction
    @Query("SELECT * FROM medicine_schedule WHERE patient_id = :patientId")
    fun getAll(patientId: Int): Flow<List<ScheduleWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM medicine_schedule WHERE id = :id")
    fun getById(id: Int): Flow<ScheduleWithDetailsDataSourceModel>

    @Transaction
    @Query("SELECT * FROM medicine_schedule WHERE medicine_id = :medicineId AND patient_id = :patientId")
    fun getByMedicine(medicineId: String, patientId: String): Flow<List<ScheduleWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM medicine_schedule WHERE medicine_id IN (:medicineIds) AND patient_id = :patientId")
    fun getByMedicine(medicineIds: List<String>, patientId: String): Flow<List<ScheduleWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM medicine_schedule WHERE is_alarm_enabled = :isAlarmEnabled")
    fun getActive(isAlarmEnabled: Boolean = true): Flow<List<ScheduleWithDetailsDataSourceModel>>

    @Transaction
    @Query("UPDATE medicine_schedule SET is_alarm_enabled = :enabled WHERE id = :id")
    suspend fun changeAlarmEnabled(id: String, enabled: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicineSchedule: MedicineScheduleDataSourceModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicineSchedules: List<MedicineScheduleDataSourceModel>): List<Long>

    @Query("DELETE FROM medicine_schedule WHERE id = :id")
    suspend fun delete(id: String)

    @Delete
    suspend fun delete(medicineSchedule: MedicineScheduleDataSourceModel)
}
