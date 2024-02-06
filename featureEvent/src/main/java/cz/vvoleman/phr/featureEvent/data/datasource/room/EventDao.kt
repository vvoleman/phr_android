package cz.vvoleman.phr.featureEvent.data.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface EventDao {

    @Transaction
    @Query("SELECT * FROM event WHERE patient_id = :patientId")
    fun getAll(patientId: String): Flow<List<EventWithDetailsDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM event WHERE id = :id")
    fun getById(id: String): Flow<EventWithDetailsDataSourceModel>

    @Transaction
    @Query("SELECT * FROM event WHERE start_at > :current")
    fun getActive(current: LocalDateTime): Flow<List<EventWithDetailsDataSourceModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: EventDataSourceModel): Long

    @Query("DELETE FROM event WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM event WHERE patient_id = :patientId")
    suspend fun deleteAll(patientId: String)


}
