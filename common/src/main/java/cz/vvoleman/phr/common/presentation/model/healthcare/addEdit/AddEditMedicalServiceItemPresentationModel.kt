package cz.vvoleman.phr.common.presentation.model.healthcare.addEdit

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import kotlin.random.Random

data class AddEditMedicalServiceItemPresentationModel(
    val id: String? = Random(System.currentTimeMillis()).nextInt(1000000).toString(),
    val facility: MedicalFacilityPresentationModel? = null,
    val serviceId: String? = null,
    val telephone: String? = null,
    val email: String? = null,
)
