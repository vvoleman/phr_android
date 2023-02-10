package cz.vvoleman.phr.data.problem_category

import androidx.room.*
import cz.vvoleman.phr.data.medical_records.MedicalRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemCategoryDao {

    @Transaction
    @Query("SELECT * FROM problem_categories")
    fun getAll(): Flow<List<ProblemCategory>>

    @Transaction
    @Query(
        "SELECT * FROM problem_categories " +
                "JOIN medical_records " +
                "ON problem_categories.id = medical_records.categoryId " +
                "WHERE problem_categories.id = :id"
    )
    fun getById(id: Int): Map<ProblemCategory, List<MedicalRecord>>

    @Insert
    suspend fun insert(problemCategory: ProblemCategory)

    @Update
    suspend fun update(problemCategory: ProblemCategory)

    @Delete
    suspend fun delete(problemCategory: ProblemCategory)

}
