package cz.vvoleman.phr.data.room.medicine

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    @Query("SELECT * FROM medicine")
    fun getAllMedicines(): Flow<List<MedicineWithSubstances>>

    @Query("SELECT * FROM medicine WHERE id = :id")
    fun getMedicineById(id: Int): Flow<MedicineWithSubstances>

    @Query("SELECT * FROM medicine WHERE name LIKE '%'||:name||'%'")
    fun getMedicineByName(name: String): Flow<List<MedicineWithSubstances>>

    @Insert
    suspend fun insert(medicine: MedicineEntity)

    @Update
    suspend fun update(medicine: MedicineEntity)

    @Delete
    suspend fun delete(medicine: MedicineEntity)

}