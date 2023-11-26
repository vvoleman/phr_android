package cz.vvoleman.phr.common.domain.eventBus

import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.common.domain.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerAdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel

object CommonEventBus {

    val getWorkerAdditionalInfoBus =
        EventBusChannel<GetMedicalWorkersAdditionalInfoEvent, Map<MedicalWorkerDomainModel, List<MedicalWorkerAdditionalInfoDomainModel>>>()
}
