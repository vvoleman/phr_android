package cz.vvoleman.phr.data.medical_records

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.data.diagnose.Diagnose
import cz.vvoleman.phr.data.facility.Facility
import cz.vvoleman.phr.data.patient.Patient

data class MedicalRecordWithDetails(
    @Embedded val medicalRecord: MedicalRecord,
    @Relation(
        parentColumn = "facilityId",
        entityColumn = "id"
    )
    val facility: Facility,
    @Relation(
        parentColumn = "patientId",
        entityColumn = "id"
    )
    val patient: Patient,
    @Relation(
        parentColumn = "diagnoseId",
        entityColumn = "id"
    )
    val diagnose: Diagnose
)