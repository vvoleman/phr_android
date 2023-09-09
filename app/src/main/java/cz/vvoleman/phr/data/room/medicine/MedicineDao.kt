package cz.vvoleman.phr.data.room.medicine

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    @Query("SELECT * FROM medicine")
    fun getAll(): Flow<List<MedicineWithSubstances>>

    @Query("SELECT * FROM medicine WHERE medicineId = :id")
    fun getById(id: String): Flow<MedicineWithSubstances>

    @Query("SELECT * FROM medicine WHERE name LIKE '%'||:name||'%'")
    fun getByName(name: String): Flow<List<MedicineWithSubstances>>

    @Insert
    suspend fun insert(medicine: MedicineEntity)

    @Insert
    suspend fun insert(medicine: MedicineSubstanceCrossRef)

    @Insert
    suspend fun insert(medicine: List<MedicineSubstanceCrossRef>)

    @Update
    suspend fun update(medicine: MedicineEntity)

    @Delete
    suspend fun delete(medicine: MedicineEntity)
}
