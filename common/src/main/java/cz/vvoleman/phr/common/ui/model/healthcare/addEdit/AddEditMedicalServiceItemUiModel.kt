package cz.vvoleman.phr.common.ui.model.healthcare.addEdit

import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel

data class AddEditMedicalServiceItemUiModel(
    val id: String,
    val facility: MedicalFacilityUiModel? = null,
    val serviceId: String? = null,
    val telephone: String? = null,
    val email: String? = null,
) {

    sealed class ItemState {
        object NoFacility : ItemState()
        data class Facility(val facility: MedicalFacilityUiModel) : ItemState()
        data class Ready(
            val facility: MedicalFacilityUiModel,
            val service: String,
            val telephone: String?,
            val email: String?
        ) : ItemState()
    }

    fun getState(): ItemState {
        return when {
            this.facility == null -> ItemState.NoFacility
            this.serviceId != null -> ItemState.Ready(
                this.facility,
                this.serviceId,
                telephone = this.telephone,
                email = this.email
            )
            else -> ItemState.Facility(this.facility)
        }
    }

}
