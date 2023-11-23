package cz.vvoleman.phr.common.data.datasource.model.healthcare.facility

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medical_facility")
data class MedicalFacilityDataSourceModel(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "facility_type") val facilityType: String,
    val street: String,
    @ColumnInfo(name = "house_number") val houseNumber: String,
    @ColumnInfo(name = "zip_code") val zipCode: String,
    val city: String,
    val region: String,
    @ColumnInfo(name = "region_code") val regionCode: String,
    val district: String,
    @ColumnInfo(name = "district_code") val districtCode: String,
    val telephone: String,
    val email: String,
    val web: String,
    val ico: String,
    @ColumnInfo(name = "provider_type") val providerType: String,
    @ColumnInfo(name = "provider_name") val providerName: String,
    val gps: String,
)
