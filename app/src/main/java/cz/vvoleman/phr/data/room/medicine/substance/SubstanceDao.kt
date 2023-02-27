package cz.vvoleman.phr.data.room.medicine.substance

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubstanceDao {

    @Query("SELECT * FROM substance")
    fun getAll(): Flow<List<SubstanceEntity>>

    @Query("SELECT * FROM substance WHERE substanceId = :id")
    fun getById(id: String): Flow<SubstanceEntity>

    @Query("SELECT * FROM substance WHERE name LIKE '%'||:name||'%'")
    fun getByName(name: String): Flow<List<SubstanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(substance: SubstanceEntity)

    @Update
    suspend fun update(substance: SubstanceEntity)

    @Delete
    suspend fun delete(substance: SubstanceEntity)
}