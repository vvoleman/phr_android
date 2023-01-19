package cz.vvoleman.phr.data.diagnose

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseGroupDao {

    @Transaction
    @Query("SELECT * FROM diagnose_groups")
    fun getAllDiagnoseGroups(): Flow<List<DiagnoseGroup>>

    @Transaction
    @Query("SELECT * FROM diagnose_groups WHERE id = :id")
    fun getDiagnoseGroupById(id: Int): Flow<DiagnoseGroup>

    @Transaction
    @Query("SELECT * FROM diagnose_groups WHERE name LIKE :name")
    fun getDiagnoseGroupByName(name: String): Flow<DiagnoseGroup>

    @Transaction
    @Query("SELECT * FROM diagnose_groups WHERE id = :id")
    fun getDiagnoseGroupByIdWithDiagnoses(id: Int): Flow<GroupWithDiagnoses>

    @Insert
    suspend fun insert(diagnoseGroup: DiagnoseGroup)

    @Update
    suspend fun update(diagnoseGroup: DiagnoseGroup)

    @Delete
    suspend fun delete(diagnoseGroup: DiagnoseGroup)

}