package cz.vvoleman.phr.data.medical_records

import androidx.room.*
import cz.vvoleman.phr.data.facility.Facility
import cz.vvoleman.phr.data.patient.Patient

@Dao
interface MedicalRecordDao {

    @Query("SELECT * FROM medical_records")
    fun getAllMedicalRecords(): List<MedicalRecord>

    @Query("SELECT * FROM medical_records WHERE id = :id")
    fun getMedicalRecordById(id: Int): MedicalRecord

    @Query("SELECT * FROM medical_records WHERE facility = :facility")
    fun getMedicalRecordByFacility(facility: Facility): MedicalRecord

    @Query("SELECT * FROM medical_records WHERE patient = :patient")
    fun getMedicalRecordByPatient(patient: Patient): MedicalRecord

    @Insert
    fun insertMedicalRecord(medicalRecord: MedicalRecord)

    @Update
    fun updateMedicalRecord(medicalRecord: MedicalRecord)

    @Delete
    fun deleteMedicalRecord(medicalRecord: MedicalRecord)

}