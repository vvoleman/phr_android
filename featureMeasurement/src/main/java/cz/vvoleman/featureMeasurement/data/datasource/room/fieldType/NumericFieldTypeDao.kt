package cz.vvoleman.featureMeasurement.data.datasource.room.fieldType

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NumericFieldTypeDao {

    @Transaction
    @Query("SELECT * FROM numeric_field_type WHERE measurement_group_id = :measurementGroupId")
    fun getByMeasurementGroup(measurementGroupId: Int): Flow<List<NumericFieldTypeDataSourceModel>>

    @Transaction
    @Query("SELECT * FROM numeric_field_type WHERE id = :id")
    fun getById(id: Int): Flow<NumericFieldTypeDataSourceModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(numericFieldType: NumericFieldTypeDataSourceModel): Long

    @Query("DELETE FROM numeric_field_type WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM numeric_field_type WHERE measurement_group_id = :measurementGroupId")
    fun deleteByMeasurementGroup(measurementGroupId: Int)

}
