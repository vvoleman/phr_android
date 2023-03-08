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
    fun getByPatientId(patientId: Int): Flow<List<ProblemCategoryEntity>>

    @Query("SELECT * FROM problem_category WHERE id = :id")
    fun getById(id: Int): Flow<ProblemCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(problemCategory: ProblemCategoryEntity)

    @Update
    suspend fun update(problemCategory: ProblemCategoryEntity)

    @Delete
    suspend fun delete(problemCategory: ProblemCategoryEntity)

}