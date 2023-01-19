package cz.vvoleman.phr.data.diagnose

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseDao {

    @Query("SELECT * FROM diagnoses")
    fun getAllDiagnoses(): Flow<List<Diagnose>>

    @Query("SELECT * FROM diagnoses WHERE id = :id")
    fun getDiagnoseById(id: Int): Flow<Diagnose>

    @Query("SELECT * FROM diagnoses WHERE name LIKE :name")
    fun getDiagnosesByName(name: String): Flow<List<Diagnose>>

    @Insert
    suspend fun insertDiagnose(diagnose: Diagnose)

    @Update
    suspend fun updateDiagnose(diagnose: Diagnose)

    @Delete
    suspend fun deleteDiagnose(diagnose: Diagnose)

}