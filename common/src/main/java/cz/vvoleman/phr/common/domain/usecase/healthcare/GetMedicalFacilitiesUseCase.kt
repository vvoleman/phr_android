package cz.vvoleman.phr.common.domain.usecase.healthcare

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.presentation.event.GetMedicalFacilitiesAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.request.GetMedicalFacilitiesRequest
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesByPatientRepository

class GetMedicalFacilitiesUseCase(
    private val eventBusChannel:
    EventBusChannel<GetMedicalFacilitiesAdditionalInfoEvent, Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>>>,
    private val getFacilitiesByPatientRepository: GetFacilitiesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<GetMedicalFacilitiesRequest, Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: GetMedicalFacilitiesRequest): Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>> {
        val facilities = getFacilitiesByPatientRepository.getFacilitiesByPatient(request.patientId)

        val event = GetMedicalFacilitiesAdditionalInfoEvent(
            medicalFacilities = facilities,
            patientId = request.patientId
        )

        val results = facilities.associateWith {
            emptyList<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>()
        }.toMutableMap()

        eventBusChannel.pushEvent(event)
            .filter { it.isNotEmpty() }
            .flatMap { it.entries }
            .onEach {
                if (results.containsKey(it.key)) {
                    results[it.key] = results[it.key]!! + it.value
                } else {
                    results[it.key] = it.value
                }
            }

        return results.toMap()
    }
}
