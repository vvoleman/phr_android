package cz.vvoleman.phr.common.domain.eventBus

import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.common.domain.event.GetMedicalFacilitiesAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.event.MedicalWorkerDeletedEvent
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel

object CommonEventBus {

    val getWorkerAdditionalInfoBus =
        EventBusChannel<GetMedicalWorkersAdditionalInfoEvent, Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>>()

    val getFacilityAdditionalInfoBus =
        EventBusChannel<GetMedicalFacilitiesAdditionalInfoEvent, Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>>>()

    val medicalWorkerDeletedEvent = EventBusChannel<MedicalWorkerDeletedEvent, Unit>()
}
