package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseGroupDao {

    @Query("SELECT * FROM diagnose_group")
    fun getAll(): Flow<List<DiagnoseGroupDataSourceModel>>

    @Query("SELECT * FROM diagnose_group WHERE id = :id")
    fun getById(id: String): Flow<DiagnoseGroupDataSourceModel>

    @Query("SELECT * FROM diagnose_group WHERE name = :name")
    fun getByName(name: String): Flow<DiagnoseGroupDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diagnoseGroup: DiagnoseGroupDataSourceModel)

    @Update
    suspend fun update(diagnoseGroup: DiagnoseGroupDataSourceModel)

    @Delete
    suspend fun delete(diagnoseGroup: DiagnoseGroupDataSourceModel)
}
