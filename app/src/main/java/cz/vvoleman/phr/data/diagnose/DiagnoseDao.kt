package cz.vvoleman.phr.data.diagnose

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseDao {

    @Query("SELECT * FROM diagnoses")
    fun getAllDiagnoses(): Flow<List<Diagnose>>

    @Query("SELECT * FROM diagnoses WHERE id = :id")
    fun getDiagnoseById(id: Int): Flow<Diagnose>

    @Query("SELECT * FROM diagnoses WHERE name LIKE '%'||:name||'%'")
    fun getDiagnosesByName(name: String): Flow<List<Diagnose>>

    @Query("SELECT * FROM diagnoses WHERE name LIKE '%'||:name||'%'")
    fun getDiagnoseWithGroupByName(name: String): Flow<List<DiagnoseWithGroup>>

    @Insert
    suspend fun insertDiagnose(diagnose: Diagnose)

    @Update
    suspend fun updateDiagnose(diagnose: Diagnose)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertOrUpdateDiagnose(diagnoses: Diagnose)

    @Delete
    suspend fun deleteDiagnose(diagnose: Diagnose)

}