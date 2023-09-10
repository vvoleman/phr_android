package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseDao {

    @Transaction
    @Query("SELECT * FROM diagnose")
    fun getAll(): Flow<List<DiagnoseWithGroup>>

    @Transaction
    @Query("SELECT * FROM diagnose WHERE id = :id")
    fun getById(id: String): Flow<DiagnoseWithGroup>

    @Transaction
    @Query("SELECT * FROM diagnose WHERE id IN (:ids)")
    fun getByIds(ids: List<String>): Flow<List<DiagnoseDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM diagnose WHERE parent = :id")
    fun getByParent(id: String): Flow<List<DiagnoseWithGroup>>

    @Transaction
    @Query("SELECT * FROM diagnose WHERE name LIKE '%'||:value||'%' OR id LIKE '%'||:value||'%'")
    fun search(value: String): Flow<List<DiagnoseWithGroup>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diagnose: DiagnoseDataSourceModel)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diagnoses: List<DiagnoseDataSourceModel>)

    @Transaction
    @Update
    suspend fun update(diagnose: DiagnoseDataSourceModel)

    @Transaction
    @Delete
    suspend fun delete(diagnose: DiagnoseDataSourceModel)
}
