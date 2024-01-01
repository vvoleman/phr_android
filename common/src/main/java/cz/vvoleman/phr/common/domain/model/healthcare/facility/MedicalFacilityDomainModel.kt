package cz.vvoleman.phr.common.domain.model.healthcare.facility

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceWithWorkersDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalFacilityDomainModel(
    val id: String,
    val fullName: String,
    val facilityType: String,
    val street: String,
    val houseNumber: String,
    val zipCode: String,
    val city: String,
    val region: String,
    val regionCode: String,
    val district: String,
    val districtCode: String,
    val telephone: String,
    val email: String,
    val web: String,
    val ico: String,
    val providerType: String,
    val providerName: String,
    val gps: String,
    val services: List<MedicalServiceWithWorkersDomainModel>,
) : Parcelable
