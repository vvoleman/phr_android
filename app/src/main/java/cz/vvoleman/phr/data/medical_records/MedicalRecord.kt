package cz.vvoleman.phr.data.medical_records

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.diagnose.Diagnose
import cz.vvoleman.phr.data.facility.Facility
import cz.vvoleman.phr.data.patient.Patient

@Entity(tableName = "medical_records")
data class MedicalRecord(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @Embedded val facility: Facility,
    @Embedded val patient: Patient,
    @Embedded val diagnose: Diagnose,
    val date: String,
    val text: String
)