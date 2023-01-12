package cz.vvoleman.phr.data.patient

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface PatientDao {

    @Query("SELECT * FROM patient")
    fun getAllPatients(): List<Patient>

    @Query("SELECT * FROM patient WHERE id = :id")
    fun getPatientById(id: Int): Patient

    @Query("SELECT * FROM patient WHERE name LIKE :name")
    fun getPatientByName(name: String): Patient

    @Insert
    fun insertPatient(patient: Patient)

    @Update
    fun updatePatient(patient: Patient)

    @Delete
    fun deletePatient(patient: Patient)

}