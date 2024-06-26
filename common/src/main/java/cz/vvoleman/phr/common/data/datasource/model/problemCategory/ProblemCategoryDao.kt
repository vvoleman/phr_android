package cz.vvoleman.phr.common.data.datasource.model.problemCategory

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemCategoryDao {

    @Query("SELECT * FROM problem_category WHERE patient_id = :patientId")
    fun getAll(patientId: String): Flow<List<ProblemCategoryDataSourceModel>>

    @Query("SELECT * FROM problem_category WHERE id = :id")
    fun getById(id: Int): Flow<ProblemCategoryDataSourceModel>

    @Query("SELECT * FROM problem_category WHERE patient_id = :patientId AND is_default = 1")
    fun getDefault(patientId: String): Flow<ProblemCategoryDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(problemCategory: ProblemCategoryDataSourceModel): Long

    @Delete
    suspend fun delete(problemCategory: ProblemCategoryDataSourceModel)

    @Query("DELETE FROM problem_category WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM problem_category WHERE patient_id = :patientId")
    suspend fun deleteByPatient(patientId: String)
}
