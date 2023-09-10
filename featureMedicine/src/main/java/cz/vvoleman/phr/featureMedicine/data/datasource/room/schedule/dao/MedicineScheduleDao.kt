package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.MedicineScheduleDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.ScheduleWithDetailsDataSourceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineScheduleDao {

    @Query("SELECT * FROM medicine_schedule WHERE patient_id = :patientId")
    fun getAll(patientId: Int): Flow<List<ScheduleWithDetailsDataSourceModel>>

    @Query("SELECT * FROM medicine_schedule WHERE id = :id")
    fun getById(id: Int): Flow<ScheduleWithDetailsDataSourceModel>

    @Query("SELECT * FROM medicine_schedule WHERE medicine_id = :medicineId AND patient_id = :patientId")
    fun getByMedicine(medicineId: String, patientId: String): Flow<List<ScheduleWithDetailsDataSourceModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicineSchedule: MedicineScheduleDataSourceModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicineSchedules: List<MedicineScheduleDataSourceModel>): List<Long>

    @Delete
    suspend fun delete(medicineSchedule: MedicineScheduleDataSourceModel)
}
