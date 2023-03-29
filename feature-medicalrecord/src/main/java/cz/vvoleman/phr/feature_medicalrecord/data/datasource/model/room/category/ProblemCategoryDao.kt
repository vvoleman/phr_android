package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemCategoryDao {

    @Query("SELECT * FROM problem_category WHERE patient_id = :patientId")
    fun getByPatientId(patientId: String): Flow<List<ProblemCategoryDataSourceModel>>

    @Query("SELECT * FROM problem_category WHERE id IN (SELECT problem_category_id FROM medical_record WHERE patient_id = :patientId)")
    fun getUsedByPatientId(patientId: String): Flow<List<ProblemCategoryDataSourceModel>>

    @Query("SELECT * FROM problem_category WHERE id = :id")
    fun getById(id: Int): Flow<ProblemCategoryDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(problemCategory: ProblemCategoryDataSourceModel)

    @Update
    suspend fun update(problemCategory: ProblemCategoryDataSourceModel)

    @Delete
    suspend fun delete(problemCategory: ProblemCategoryDataSourceModel)

}