package cz.vvoleman.phr.common.data.datasource.model.healthcare.facility

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDataSourceModel

data class MedicalFacilityWithDetailsDataSourceModel(
    @Embedded val medicalFacility: MedicalFacilityDataSourceModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "medical_facility_id"
    )
    val services: List<MedicalServiceDataSourceModel>,
)
