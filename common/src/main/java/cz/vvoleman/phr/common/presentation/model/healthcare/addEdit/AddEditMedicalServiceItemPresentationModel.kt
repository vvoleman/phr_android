package cz.vvoleman.phr.common.presentation.model.healthcare.addEdit

import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import kotlin.random.Random

data class AddEditMedicalServiceItemPresentationModel(
    val id: String? = Random(System.currentTimeMillis()).nextInt(UNTIL).toString(),
    val facility: MedicalFacilityPresentationModel? = null,
    val serviceId: String? = null,
    val telephone: String? = null,
    val email: String? = null,
) {
    companion object {
        private const val UNTIL = 1000000
    }
}
