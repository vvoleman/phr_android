package cz.vvoleman.phr.common.data.datasource.model.healthcare.service

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medical_service")
data class MedicalServiceDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "care_field") val careField: String,
    @ColumnInfo(name = "care_form") val careForm: String,
    @ColumnInfo(name = "care_type") val careType: String,
    @ColumnInfo(name = "care_extent") val careExtent: String,
    @ColumnInfo(name = "bed_count") val bedCount: Int,
    @ColumnInfo(name = "service_note") val serviceNote: String,
    @ColumnInfo(name = "professional_representative") val professionalRepresentative: String,
    @ColumnInfo(name = "medical_facility_id") val medicalFacilityId: String
)
