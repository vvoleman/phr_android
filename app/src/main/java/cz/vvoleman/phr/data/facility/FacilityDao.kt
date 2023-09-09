package cz.vvoleman.phr.data.facility

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FacilityDao {

    @Query("SELECT * FROM facilities")
    fun getAllFacilities(): List<Facility>

    @Query("SELECT * FROM facilities WHERE id = :id")
    fun getFacilityById(id: Int): Facility

    @Query("SELECT * FROM facilities WHERE name LIKE :name")
    fun getFacilityByName(name: String): Facility

    @Insert
    fun insertFacility(facility: Facility)

    @Update
    fun updateFacility(facility: Facility)

    @Delete
    fun deleteFacility(facility: Facility)
}
