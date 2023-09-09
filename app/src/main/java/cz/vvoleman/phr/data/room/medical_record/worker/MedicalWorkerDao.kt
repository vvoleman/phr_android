package cz.vvoleman.phr.data.room.medical_record.worker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalWorkerDao {

    @Query("SELECT * FROM medical_worker")
    fun getAll(): Flow<List<MedicalWorkerEntity>>

    @Query("SELECT * FROM medical_worker WHERE id = :id")
    fun getById(id: Int): Flow<MedicalWorkerEntity>

    @Query("SELECT * FROM medical_worker WHERE name = :name")
    fun getByName(name: String): Flow<MedicalWorkerEntity>

    @Insert
    suspend fun insert(medicalWorker: MedicalWorkerEntity)

    @Update
    suspend fun update(medicalWorker: MedicalWorkerEntity)

    @Delete
    suspend fun delete(medicalWorker: MedicalWorkerEntity)
}
