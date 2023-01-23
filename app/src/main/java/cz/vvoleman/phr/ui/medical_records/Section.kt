package cz.vvoleman.phr.ui.medical_records

import cz.vvoleman.phr.data.medical_records.MedicalRecordWithDetails

data class Section(
    val name: String,
    val items: List<MedicalRecordWithDetails>
)
