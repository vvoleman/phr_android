package cz.vvoleman.phr.common.presentation.model.healthcare.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalServicePresentationModel(
    val id: String,
    val careField: String,
    val careForm: String,
    val careType: String,
    val careExtent: String,
    val bedCount: Int,
    val serviceNote: String,
    val professionalRepresentative: String,
    val medicalFacilityId: String
) : Parcelable
