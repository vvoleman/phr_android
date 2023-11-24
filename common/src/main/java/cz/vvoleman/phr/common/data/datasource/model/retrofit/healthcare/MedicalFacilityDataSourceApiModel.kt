package cz.vvoleman.phr.common.data.datasource.model.retrofit.healthcare

import com.google.gson.annotations.SerializedName

data class MedicalFacilityDataSourceApiModel(
    val id: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("facility_code") val facilityCode: String,
    @SerializedName("facility_type_code") val facilityTypeCode: String,
    @SerializedName("facility_type") val facilityType: String,
    @SerializedName("secondary_facility_type") val secondaryFacilityType: String,
    val founder: String,
    val city: String,
    @SerializedName("postal_code") val postalCode: String,
    val street: String,
    @SerializedName("house_number_orientation") val houseNumberOrientation: String,
    val region: String,
    @SerializedName("region_code") val regionCode: String,
    val district: String,
    @SerializedName("district_code") val districtCode: String,
    @SerializedName("administrative_district") val administrativeDistrict: String,
    @SerializedName("provider_telephone") val providerTelephone: String,
    @SerializedName("provider_fax") val providerFax: String,
    @SerializedName("activity_started_at") val activityStartedAt: ActivityStartedAt,
    @SerializedName("provider_email") val providerEmail: String,
    @SerializedName("provider_web") val providerWeb: String,
    @SerializedName("provider_type") val providerType: String,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("identification_number") val identificationNumber: String,
    @SerializedName("person_type") val personType: String,
    @SerializedName("region_code_of_domicile") val regionCodeOfDomicile: String,
    @SerializedName("domicile_region") val domicileRegion: String,
    @SerializedName("district_code_of_domicile") val districtCodeOfDomicile: String,
    @SerializedName("domicile_district") val domicileDistrict: String,
    @SerializedName("postal_code_of_domicile") val postalCodeOfDomicile: String,
    @SerializedName("domicile_city") val domicileCity: String,
    @SerializedName("domicile_street") val domicileStreet: String,
    @SerializedName("domicile_house_number_orientation") val domicileHouseNumberOrientation: String,
    val gps: String,
    val services: List<MedicalServiceDataSourceApiModel>
)

data class ActivityStartedAt(
    val date: String,
    @SerializedName("timezone_type") val timezoneType: Int,
    val timezone: String
)
