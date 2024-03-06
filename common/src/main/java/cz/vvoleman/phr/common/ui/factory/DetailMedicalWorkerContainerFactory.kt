package cz.vvoleman.phr.common.ui.factory

import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.SpecificMedicalWorkerUiModel
import cz.vvoleman.phr.common.ui.view.healthcare.detailMedicalWorker.groupie.ContactItem
import cz.vvoleman.phr.common.ui.view.healthcare.detailMedicalWorker.groupie.SpecificWorkerContainer
import cz.vvoleman.phr.common_datasource.R

class DetailMedicalWorkerContainerFactory(
    private val copyManager: CopyManager
) {

    fun create(
        specificWorkers: List<SpecificMedicalWorkerUiModel>,
        facilities: List<MedicalFacilityUiModel>
    ): List<BindableItem<*>> {
        val containers = mutableListOf<SpecificWorkerContainer>()

        for (worker in specificWorkers) {
            val facility = facilities.firstOrNull { it.id == worker.medicalService?.medicalFacilityId } ?: continue
            val contacts = getContactItems(worker, facility)

            val container = SpecificWorkerContainer(
                facility = facility,
                items = contacts
            )
            containers.add(container)
        }

        return containers
    }

    private fun getContactItems(
        worker: SpecificMedicalWorkerUiModel,
        facility: MedicalFacilityUiModel
    ): List<ContactItem> {
        return listOf(
            ContactItem(
                icon = R.drawable.ic_phone,
                value = worker.telephone,
                buttonText = R.string.action_contact_phone,
                onClick = { phone ->
                    copyManager.copy(phone)
                }
            ),
            ContactItem(
                icon = R.drawable.ic_mail,
                value = worker.email,
                buttonText = R.string.action_contact_email,
                onClick = { email ->
                    copyManager.copy(email)
                }
            ),
            ContactItem(
                icon = R.drawable.ic_map,
                value = facility.getFullAddress(),
                buttonText = R.string.action_contact_address,
                onClick = { address ->
                    copyManager.copy(address)
                }
            )
        )
    }

}
